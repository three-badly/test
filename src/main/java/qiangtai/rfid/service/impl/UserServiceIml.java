package qiangtai.rfid.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
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

import qiangtai.rfid.dto.req.*;
import qiangtai.rfid.dto.rsp.UserNameInfo;
import qiangtai.rfid.dto.rsp.UserResultVO;
import qiangtai.rfid.entity.Company;
import qiangtai.rfid.entity.User;
import qiangtai.rfid.handler.exception.BusinessException;
import qiangtai.rfid.mapper.CompanyMapper;
import qiangtai.rfid.mapper.UserMapper;
import qiangtai.rfid.service.CompanyService;
import qiangtai.rfid.service.UserService;
import qiangtai.rfid.utils.TokenUtil;

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
        String account = loginVO.getAccount();
        String password = loginVO.getPassword();
        User user = userMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getAccount, account)
        );
        if (user == null) {
            throw new BusinessException(10008, "账号不存在");
        }
        String salt = user.getSalt();
        //密码盐值
        String defPassword = SecureUtil.sha256(password + salt);

        if (!defPassword.equals(user.getPassword())) {
            throw new BusinessException(10009, "密码错误");
        }
        UserResultVO userResultVO = BeanUtil.copyProperties(user, UserResultVO.class);
        Company company = new Company();
        company.setCompanyName("");
        if (userResultVO.getCompanyId() != -1) {
            company = companyMapper.selectOne(Wrappers.<Company>lambdaQuery().eq(Company::getId, userResultVO.getCompanyId()));
            if (company == null) {
                throw new BusinessException(10008, "公司不存在");
            }
        }
        Company finalCompany = company;
        //生成token
        userResultVO.setToken(TokenUtil.createToken(user, finalCompany));
        //获取公司名字
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
        Page<UserResultVO> page = new Page<>(userQuery.getCurrent(), userQuery.getSize());
        /* 公共连表 + 过滤 + 分页逻辑 */
        return getWrapper(userQuery).page(page, UserResultVO.class);
    }

    @Override
    public List<UserResultVO> listUser(UserQuery userQuery) {
        /* 公共连表 + 过滤 + 分页逻辑 */
        return getWrapper(userQuery).list(UserResultVO.class);

    }

    public MPJLambdaWrapper<User> getWrapper(UserQuery userQuery) {
        Integer companyId = UserContext.get().getCompanyId();
        return new MPJLambdaWrapper<>(User.class)
                // user 全部字段
                .selectAll(User.class)
                // 拉 company_name
                .select(Company::getCompanyName)
                .leftJoin(Company.class, Company::getId, User::getCompanyId)
                // 永远过滤掉平台管理员
                .ne(User::getCompanyId, -1)
                // 普通企业管理员只能看本公司
                .eq(companyId != -1, User::getCompanyId, companyId)
                // 后续还可加模糊查询
                .like(StringUtils.isNotBlank(userQuery.getUsername()),
                        User::getUsername, userQuery.getUsername())
                .like(StringUtils.isNotBlank(userQuery.getCompanyName()),
                        Company::getCompanyName, userQuery.getCompanyName())
                // 排序（可选，按 id 倒序）
                .orderByDesc(User::getId);
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

    @Override
    public Boolean resetPassword(ResetPasswordVO resetPasswordVO) {
        if (UserContext.get().getCompanyId() != -1){
            throw new BusinessException(10008, "当前账号权限不足");
        }
        User user = this.getById(resetPasswordVO.getId());
        user.setPassword(SecureUtil.sha256(resetPasswordVO.getPassword() + user.getSalt()));
        return this.updateById(user);
    }
}
