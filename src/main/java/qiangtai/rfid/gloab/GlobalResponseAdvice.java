package qiangtai.rfid.gloab;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import qiangtai.rfid.dto.result.IgnoreWrapper;
import qiangtai.rfid.dto.result.Result;

@RestControllerAdvice
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

    /* 只要没加 @IgnoreWrapper 就统一包 */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        /* 1. 方法上贴了 @IgnoreWrapper 就跳过 */
        if (returnType.hasMethodAnnotation(IgnoreWrapper.class)) {
            return false;
        }
        /* 2. SpringDoc / Swagger 自己的控制器 */
        String pkg = returnType.getDeclaringClass().getPackage().getName();
        if (pkg.startsWith("org.springdoc") || pkg.startsWith("springfox")) {
            return false;
        }
        /* 3. 返回类型已经是 OpenAPI / Swagger 模型 */
        Class<?> returnClass = returnType.getParameterType();
        if (returnClass.getName().contains("OpenAPI") ||
                returnClass.getName().contains("Swagger")) {
            return false;
        }
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        /* 已经是 Result 就不重复包 */
        if (body instanceof Result) {
            return body;
        }

        /* String 类型需要手动转，否则会抛 ClassCastException */
        if (body instanceof String) {
            return JSONObject.toJSONString(Result.ok(body));
        }
        return Result.ok(body);
    }
}