package qiangtai.rfid.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import qiangtai.rfid.constant.Constant;
import qiangtai.rfid.context.UserContext;
import qiangtai.rfid.dto.LoginVO;

import qiangtai.rfid.dto.req.UserMobileNameUpadteVO;
import qiangtai.rfid.dto.req.UserQuery;
import qiangtai.rfid.dto.req.UserSaveVO;
import qiangtai.rfid.dto.req.UserUpdatePasswordVO;
import qiangtai.rfid.dto.rsp.UserNameInfo;
import qiangtai.rfid.dto.rsp.UserResultVO;
import qiangtai.rfid.entity.Company;
import qiangtai.rfid.entity.User;
import qiangtai.rfid.handler.exception.BusinessException;
import qiangtai.rfid.mapper.CompanyMapper;
import qiangtai.rfid.mapper.UserMapper;
import qiangtai.rfid.service.CompanyService;
import qiangtai.rfid.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceIml extends ServiceImpl<UserMapper, User>
        implements UserService {
    private final UserMapper userMapper;
    private final CompanyMapper companyMapper;
    private final CompanyService companyService;

    @Override
    public UserResultVO login(LoginVO loginVO) {
        log.info("用户登录：{}", loginVO);
        String username = loginVO.getAccount();
        String password = loginVO.getPassword();
        User user = userMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getAccount, username)
        );
        if (user == null) {
            throw new BusinessException(10008, "用户不存在");
        }
        String salt = user.getSalt();
        //密码盐值
        String defPassword = SecureUtil.sha256(password + salt);

        if (!defPassword.equals(user.getPassword())) {
            throw new BusinessException(10009, "密码错误");
        }
        UserResultVO userResultVO = BeanUtil.copyProperties(user, UserResultVO.class);
        Company company = new Company();
        company.setCompanyName("平台管理员");
        if (userResultVO.getCompanyId() != -1) {
            company = companyMapper.selectOne(Wrappers.<Company>lambdaQuery().eq(Company::getId, userResultVO.getCompanyId()));
            if (company == null) {
                throw new BusinessException(10008, "公司不存在");
            }
        }
        //生成token
        Company finalCompany = company;
        Map<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;

            {
                put(Constant.TOKEN_USER_ID, user.getId());
                put(Constant.TOKEN_COMPANY_ID, user.getCompanyId());
                put(Constant.TOKEN_COMPANY_NAME, finalCompany.getCompanyName());
                put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15);
            }
        };

        String token = JWTUtil.createToken(map, Constant.TOKEN_SECRET.getBytes());


        userResultVO.setToken(token);


        userResultVO.setCompanyName(company.getCompanyName());
        return userResultVO;
    }

    @Override
    public UserNameInfo addUser(UserSaveVO userSaveVO) {
        Integer companyId = UserContext.get().getCompanyId();
        if (companyId != -1) {
            throw new BusinessException(10008, "权限不足");
        }

        if (userSaveVO.getCompanyId() == -1 || userSaveVO.getAccount().equals(Constant.ROOT_NAME)) {
            throw new BusinessException(10008, "名字不可为" + Constant.ROOT_NAME);
        }
        //校验companyId是否存在
        Company company = companyMapper.selectOne(Wrappers.<Company>lambdaQuery()
                .eq(Company::getId, userSaveVO.getCompanyId())
        );
        if (company == null) {
            throw new BusinessException(10008, "公司不存在");
        }

        List<String> list = userMapper.selectList(Wrappers.<User>lambdaQuery())
                .stream()
                .map(User::getAccount)
                .collect(Collectors.toList());
        //查看是否账号名重复
        if (list.contains(userSaveVO.getAccount())) {
            throw new BusinessException(10008, "用户名已存在");
        }
        User user = BeanUtil.copyProperties(userSaveVO, User.class);
        //密码盐值
        String salt = RandomUtil.randomString(20);
        String defPassword = SecureUtil.sha256(user.getPassword() + salt);
        user.setSalt(salt);
        user.setPassword(defPassword);

        userMapper.insert(user);
        UserNameInfo userNameInfo = BeanUtil.copyProperties(userSaveVO, UserNameInfo.class);
        userNameInfo.setCompanyName(company.getCompanyName());
        return userNameInfo;
    }

    @Override
    public Page<UserResultVO> pageUser(UserQuery userQuery) {
        List<Company> companyList = companyService.getCompanyList();
        Map<Integer, String> companyMap = companyList.stream()
                .collect(Collectors.toMap(Company::getId, Company::getCompanyName));
        Page<UserResultVO> page = new Page<>(userQuery.getCurrent(), userQuery.getSize());
        Integer companyId = UserContext.get().getCompanyId();

        //平台管理员
        if (companyId == -1) {
//             2. 用 MPJ 的 Lambda 连表
            // 插件提供的构造器
            return new MPJLambdaWrapper<User>(User.class)
                    // user 表全部字段
                    .selectAll(User.class)
                    // 再拉 company_name
                    .select(Company::getCompanyName)
                    // 左连接
                    .leftJoin(Company.class, Company::getId, User::getCompanyId)
                    // 过滤掉平台管理员（company_id = -1）
                    .ne(User::getCompanyId, -1)
                    .like(StringUtils.isNotBlank(userQuery.getUsername()),
                            // 模糊查用户名
                            User::getUsername, userQuery.getUsername())
                    // 模糊查公司名
                    .like(StringUtils.isNotBlank(userQuery.getCompanyName()),
                            Company::getCompanyName, userQuery.getCompanyName())
                    // 插件自动 count + 分页
                    .page(page, UserResultVO.class);
        }

//            Page<User> page1 = this.page(new Page<>(userQuery.getCurrent(), userQuery.getSize()), Wrappers
//                    .<User>lambdaQuery()
//                    //可模糊查询
//                    .like(StringUtils.isNotBlank(userQuery.getUsername()), User::getUsername, userQuery.getUsername())
//            );
//            //过滤掉公司id为-1的平台管理员，并赋值公司名称
//            List<UserResultVO> collect = page1.getRecords().stream().filter(user -> user.getCompanyId() != -1).map(user -> {
//                UserResultVO userResultVO = BeanUtil.copyProperties(user, UserResultVO.class);
//                userResultVO.setCompanyName(companyMap.get(user.getCompanyId()));
//                return userResultVO;
//            }).collect(Collectors.toList());
//            BeanUtil.copyProperties(page1, page);
//            page.setRecords(collect);
//            return page;}

        //公司管理员只筛选公司下的用户
        Page<User> page1 = this.page(new Page<>(userQuery.getCurrent(), userQuery.getSize()), Wrappers.<User>lambdaQuery()
                .eq(User::getCompanyId, companyId)
                //可模糊查询
                .like(StringUtils.isNotBlank(userQuery.getUsername()), User::getUsername, userQuery.getUsername())
        );
        List<UserResultVO> collect = page1.getRecords().stream().map(user -> {
            UserResultVO userResultVO = BeanUtil.copyProperties(user, UserResultVO.class);
            userResultVO.setCompanyName(companyMap.get(user.getCompanyId()));
            return userResultVO;
        }).collect(Collectors.toList());
        BeanUtil.copyProperties(page1, page);
        page.setRecords(collect);
        return page;
    }

    @Override
    public List<UserResultVO> listUser() {
        //回显公司名称
        List<Company> companyList = companyService.getCompanyList();
        Map<Integer, String> companyMap = companyList.stream()
                .collect(Collectors.toMap(Company::getId, Company::getCompanyName));
        Integer companyId = UserContext.get().getCompanyId();
        return this.list(Wrappers.<User>lambdaQuery()
                        .eq(companyId != -1, User::getCompanyId, companyId)
                ).stream()
                //过滤掉公司id为-1的平台管理员
                .filter(user -> user.getCompanyId() != -1)
                //封装结果集
                .map(user -> {
                    UserResultVO userResultVO = BeanUtil.copyProperties(user, UserResultVO.class);
                    userResultVO.setCompanyName(companyMap.get(user.getCompanyId()));
                    return userResultVO;
                }).collect(Collectors.toList());

    }

    @Override
    public Boolean updatePassword(UserUpdatePasswordVO userUpdatePasswordVO) {

        Integer userId = UserContext.get().getUserId();
        if (!Objects.equals(userUpdatePasswordVO.getId(), userId)) {
            throw new BusinessException(10008, "只能修改自己的密码");
        }
        User user = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getId, userUpdatePasswordVO.getId()));
        String salt = user.getSalt();
        //密码盐值
        String defPassword = SecureUtil.sha256(userUpdatePasswordVO.getOldPassword() + salt);

        if (!defPassword.equals(user.getPassword())) {
            throw new BusinessException(10009, "旧密码错误");
        }
        defPassword = SecureUtil.sha256(userUpdatePasswordVO.getNewPassword() + salt);
        user.setPassword(defPassword);

        return this.updateById(user);
    }

    @Override
    public Boolean updateMobileName(UserMobileNameUpadteVO userMobileNameUpadteVO) {
        //限定权限
        if (!Objects.equals(UserContext.get().getUserId(), userMobileNameUpadteVO.getId())) {
            throw new BusinessException(10008, "只能修改自己的信息");
        }
        //修改账号
        List<String> list = userMapper.selectList(Wrappers.<User>lambdaQuery()
                        .ne(User::getId, userMobileNameUpadteVO.getId()))
                .stream()
                .map(User::getAccount)
                .collect(Collectors.toList());
        //查看是否账号名重复
        if (list.contains(userMobileNameUpadteVO.getAccount())) {
            throw new BusinessException(10008, "登录账号存在相同名，修改失败");
        }
        //修改手机号

        //修改账号持有人名字
        return this.updateById(BeanUtil.copyProperties(userMobileNameUpadteVO, User.class));
    }

    @Override
    public UserResultVO detail(Integer id) {
        User user = this.getById(id);
        return BeanUtil.copyProperties(user, UserResultVO.class);
    }

    @Override
    public Boolean deleteUser(Integer id) {
        //一级管理员（平台管理员）才能删除账号
        if (UserContext.get().getCompanyId() != -1) {
            throw new BusinessException(10008, "当前账号权限不足");
        }
        //确定删除账号id是否存在
        User user = this.getById(id);
        if (user == null) {
            throw new BusinessException(10013, "账号不存在");
        }

        return this.removeById(id);
    }
}
