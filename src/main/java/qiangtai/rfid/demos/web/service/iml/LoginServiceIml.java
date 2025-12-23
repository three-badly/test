package qiangtai.rfid.demos.web.service.iml;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import qiangtai.rfid.demos.web.constant.Constant;
import qiangtai.rfid.demos.web.dto.LoginVO;
import qiangtai.rfid.demos.web.dto.UserResultVO;
import qiangtai.rfid.demos.web.entity.User;
import qiangtai.rfid.demos.web.handler.exception.BusinessException;
import qiangtai.rfid.demos.web.mapper.LoginMapper;
import qiangtai.rfid.demos.web.service.LoginService;
import qiangtai.rfid.demos.web.utils.DigestUtil;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceIml implements LoginService {
    private final LoginMapper loginMapper;

    @Override
    public UserResultVO login(LoginVO loginVO) {
        log.info("用户登录：{}", loginVO);
        String username = loginVO.getUsername();
        String password = loginVO.getPassword();
        User user = loginMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getUsername, username)
                .eq(User::getPassword, DigestUtil.get(password))
                .eq(User::getCompanyId, loginVO.getCompanyId()));
        if (user == null) {
            throw new BusinessException(10008, "用户名或密码或身份错误");
        }
        //生成token
        Map<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;
            {
                put("identity", user.getIdentity());
                put("id", user.getId());
                put("companyId", user.getCompanyId());
                put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15);
            }
        };

        String token = JWTUtil.createToken(map, Constant.TOKEN_SECRET.getBytes());

        UserResultVO userResultVO = BeanUtil.copyProperties(user, UserResultVO.class);
        userResultVO.setToken(token);
        return userResultVO;
    }

    @Override
    public UserResultVO register(LoginVO user) {
        return null;
    }
}
