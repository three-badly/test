package qiangtai.rfid.utils;

import cn.hutool.jwt.JWTUtil;
import qiangtai.rfid.constant.Constant;
import qiangtai.rfid.entity.Company;
import qiangtai.rfid.entity.User;

import java.util.HashMap;
import java.util.Map;

public class TokenUtil {
    public static String createToken(User user, Company finalCompany) {
        Map<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;
            {
                put(Constant.TOKEN_USER_ID, user.getId());
                put(Constant.TOKEN_COMPANY_ID, user.getCompanyId());
                put(Constant.TOKEN_ACCOUNT, user.getAccount());
                put(Constant.TOKEN_USER_NAME, user.getUsername());
                put(Constant.TOKEN_COMPANY_NAME, finalCompany.getCompanyName());
                put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15);
            }
        };

        return JWTUtil.createToken(map, Constant.TOKEN_SECRET.getBytes());
    }
}
