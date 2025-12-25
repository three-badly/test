package qiangtai.rfid.interceptor;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import qiangtai.rfid.constant.Constant;
import qiangtai.rfid.context.UserContext;
import qiangtai.rfid.handler.exception.BusinessException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static final String[] NEED_TOKEN = {"/api", "/company", "/dept"};

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String token = request.getHeader(Constant.HEADER_TOKEN);
        if (token == null || !token.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"msg\":\"token无效\"}");
            return false;
        }
        token = token.substring(7);

        if (!JWTUtil.verify(token, Constant.TOKEN_SECRET.getBytes())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"msg\":\"token无效\"}");
            return false;
        }

        JWT jwt = JWTUtil.parseToken(token);
        Map<String, Object> payload = jwt.getPayloads();
        // 安全写法
        Integer userId    = ((Number) payload.get(Constant.TOKEN_USER_ID)).intValue();
        Integer companyId = ((Number) payload.get(Constant.TOKEN_COMPANY_ID)).intValue();

        // 放入 ThreadLocal 上下文
        UserContext.set(new UserContext.UserInfo(userId, companyId));
        log.warn("【TokenInterceptor】ThreadLocal 已写入 userId={},companyId={}", userId,companyId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {
        // 请求结束务必清理，防止线程复用导致串号

        UserContext.clear();
        log.warn("【TokenInterceptor】ThreadLocal 已清理");
    }

}