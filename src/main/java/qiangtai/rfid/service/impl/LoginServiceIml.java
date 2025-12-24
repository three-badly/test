package qiangtai.rfid.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import qiangtai.rfid.constant.Constant;
import qiangtai.rfid.dto.LoginVO;

import qiangtai.rfid.dto.rsp.UserNameInfo;
import qiangtai.rfid.dto.rsp.UserResultVO;
import qiangtai.rfid.entity.Company;
import qiangtai.rfid.entity.User;
import qiangtai.rfid.handler.exception.BusinessException;
import qiangtai.rfid.mapper.CompanyMapper;
import qiangtai.rfid.mapper.LoginMapper;
import qiangtai.rfid.service.LoginService;
import qiangtai.rfid.utils.DigestUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceIml implements LoginService {
    private final LoginMapper loginMapper;
    private final CompanyMapper companyMapper;

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

        if (!(DigestUtil.get(password).equals(user.getPassword()))){
            throw new BusinessException(10009, "密码错误");
        }
        //生成token
        Map<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;
            {
                put("id", user.getId());
                put("companyId", user.getCompanyId());
                put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15);
            }
        };

        String token = JWTUtil.createToken(map, Constant.TOKEN_SECRET.getBytes());

        UserResultVO userResultVO = BeanUtil.copyProperties(user, UserResultVO.class);
        userResultVO.setToken(token);
        if (!Objects.equals(user.getCompanyId(), "-1")){
            Company company = companyMapper.selectOne(Wrappers.<Company>lambdaQuery().eq(Company::getId, userResultVO.getCompanyId()));
            userResultVO.setCompanyName(company.getName());
        }else {
            userResultVO.setCompanyName("平台管理员");
        }
        return userResultVO;
    }

    @Override
    public UserResultVO register(LoginVO user) {
        return null;
    }

    @Override
    public UserNameInfo addUser(String companyId) {
        //校验companyId是否存在
        Company company = companyMapper.selectOne(Wrappers.<Company>lambdaQuery()
                .eq(Company::getId, companyId)
        );
        if (company == null){
            throw new BusinessException(10008, "公司不存在");
        }
        //校验是否多个账户（需求是否需要）todo
        //随机生成6位数字符串账号和6位数的密码
        List<String> list = loginMapper.selectList(Wrappers.<User>lambdaQuery())
                .stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
        //查看是否账号名重复，重复则重新生成
        String s ;
        do {
            s = RandomUtil.randomString(6);
        }while (list.contains(s));
        User user = new User();
        user.setUsername(s);
        String password = RandomUtil.randomString(6);
        user.setPassword(DigestUtil.get(password));
        user.setCompanyId(companyId);
        loginMapper.insert(user);
        user.setPassword(password);
        UserNameInfo userNameInfo = BeanUtil.copyProperties(user, UserNameInfo.class);
        userNameInfo.setName(company.getName());
        return userNameInfo;
    }
}
