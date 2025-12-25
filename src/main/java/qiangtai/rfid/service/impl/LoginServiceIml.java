package qiangtai.rfid.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import qiangtai.rfid.constant.Constant;
import qiangtai.rfid.context.UserContext;
import qiangtai.rfid.dto.LoginVO;

import qiangtai.rfid.dto.req.UserQuery;
import qiangtai.rfid.dto.req.UserSaveVO;
import qiangtai.rfid.dto.rsp.UserNameInfo;
import qiangtai.rfid.dto.rsp.UserResultVO;
import qiangtai.rfid.entity.Company;
import qiangtai.rfid.entity.User;
import qiangtai.rfid.handler.exception.BusinessException;
import qiangtai.rfid.mapper.CompanyMapper;
import qiangtai.rfid.mapper.LoginMapper;
import qiangtai.rfid.service.CompanyService;
import qiangtai.rfid.service.LoginService;
import qiangtai.rfid.utils.DigestUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceIml extends ServiceImpl<LoginMapper, User>
        implements LoginService {
    private final LoginMapper loginMapper;
    private final CompanyMapper companyMapper;
    private final CompanyService companyService;

    @Override
    public UserResultVO login(LoginVO loginVO) {
        log.info("用户登录：{}", loginVO);
        String username = loginVO.getUsername();
        String password = loginVO.getPassword();
        User user = loginMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getUsername, username)
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
        //生成token
        Map<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;

            {
                put(Constant.TOKEN_USER_ID, user.getId());
                put(Constant.TOKEN_COMPANY_ID, user.getCompanyId());
                put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15);
            }
        };

        String token = JWTUtil.createToken(map, Constant.TOKEN_SECRET.getBytes());

        UserResultVO userResultVO = BeanUtil.copyProperties(user, UserResultVO.class);
        userResultVO.setToken(token);
        Company company = new Company();
        company.setCompanyName("平台管理员");
        if (userResultVO.getCompanyId() != -1) {
            company = companyMapper.selectOne(Wrappers.<Company>lambdaQuery().eq(Company::getId, userResultVO.getCompanyId()));
            if (company == null) {
                throw new BusinessException(10008, "公司不存在");
            }
        }

        userResultVO.setCompanyName(company.getCompanyName());
        return userResultVO;
    }

    @Override
    public UserNameInfo addUser(UserSaveVO userSaveVO) {
        //todo 谁能新加账号？
        if (userSaveVO.getCompanyId() == -1 || userSaveVO.getUsername().equals(Constant.ROOT_NAME)) {
            throw new BusinessException(10008, "名字不可为" + Constant.ROOT_NAME);
        }
        //校验companyId是否存在
        Company company = companyMapper.selectOne(Wrappers.<Company>lambdaQuery()
                .eq(Company::getId, userSaveVO.getCompanyId())
        );
        if (company == null) {
            throw new BusinessException(10008, "公司不存在");
        }
        //校验是否多个账户（需求是否需要）todo
        List<String> list = loginMapper.selectList(Wrappers.<User>lambdaQuery())
                .stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
        //查看是否账号名重复
        if (list.contains(userSaveVO.getUsername())) {
            throw new BusinessException(10008, "用户名已存在");
        }
        User user = BeanUtil.copyProperties(userSaveVO, User.class);
        //密码盐值
        String salt = RandomUtil.randomString(20);
        String defPassword = SecureUtil.sha256(user.getPassword() + salt);
        user.setSalt(salt);
        user.setPassword(defPassword);

        loginMapper.insert(user);
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
            Page<User> page1 = this.page(new Page<>(userQuery.getCurrent(), userQuery.getSize()), Wrappers.<User>emptyWrapper());
            //过滤掉公司id为-1的平台管理员，并赋值公司名称
            List<UserResultVO> collect = page1.getRecords().stream().filter(user -> user.getCompanyId() != -1).map(user -> {
                UserResultVO userResultVO = BeanUtil.copyProperties(user, UserResultVO.class);
                userResultVO.setCompanyName(companyMap.get(user.getCompanyId()));
                return userResultVO;
            }).collect(Collectors.toList());
            BeanUtil.copyProperties(page1, page);
            page.setRecords(collect);
            return page;
        }
        //公司管理员只筛选公司下的用户
        Page<User> page1 = this.page(new Page<>(userQuery.getCurrent(), userQuery.getSize()), Wrappers.<User>lambdaQuery()
                .eq(User::getCompanyId, companyId));
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
        List<Company> companyList = companyService.getCompanyList();
        Map<Integer, String> companyMap = companyList.stream()
                .collect(Collectors.toMap(Company::getId, Company::getCompanyName));
        Integer companyId = UserContext.get().getCompanyId();
        if (companyId == -1) {
            return this.list(Wrappers.<User>lambdaQuery()
                    .ne(User::getCompanyId, companyId)
            ).stream().map(user -> {
                UserResultVO userResultVO = BeanUtil.copyProperties(user, UserResultVO.class);
                userResultVO.setCompanyName(companyMap.get(user.getCompanyId()));
                return userResultVO;
            }).collect(Collectors.toList());
        }
        return this.list(Wrappers.<User>lambdaQuery()
                .eq(User::getCompanyId, companyId)
        ).stream().map(user -> {
            UserResultVO userResultVO = BeanUtil.copyProperties(user, UserResultVO.class);
            userResultVO.setCompanyName(companyMap.get(user.getCompanyId()));
            return userResultVO;
        }).collect(Collectors.toList());

    }
}
