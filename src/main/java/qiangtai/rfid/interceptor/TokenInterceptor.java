package qiangtai.rfid.interceptor;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import qiangtai.rfid.constant.Constant;
import qiangtai.rfid.context.UserContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

// 省略 import
@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static final String[] NEED_TOKEN = {"/api", "/company", "/dept"};

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String uri = request.getRequestURI();

        String token = request.getHeader(Constant.HEADER_TOKEN);
        if (token == null || !token.startsWith("Bearer ")) {
            log.warn("【TokenInterceptor】token is null");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        token = token.substring(7);

        if (!JWTUtil.verify(token, Constant.TOKEN_SECRET.getBytes())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        JWT jwt = JWTUtil.parseToken(token);
        Map<String, Object> payload = jwt.getPayloads();
        String userId    = String.valueOf(payload.get(Constant.TOKEN_USER_ID));
        String companyId = String.valueOf(payload.get(Constant.TOKEN_COMPANY_ID));

        // 放入 ThreadLocal 上下文
        UserContext.set(new UserContext.UserInfo(userId, companyId));
        log.error("【TokenInterceptor】ThreadLocal 已写入 userId={}", userId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {
        // 请求结束务必清理，防止线程复用导致串号
        UserContext.clear();
    }

}