package qiangtai.rfid.aspect;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Aspect
@Component
@Slf4j
public class LogAspect {

    private final static Logger LOG = LoggerFactory.getLogger(LogAspect.class);
    //注入 Spring 已经注册好 JavaTimeModule 的 ObjectMapper
    @Autowired
    private ObjectMapper objectMapper;
    /**
     * 定义一个切点
     */
    @Pointcut("execution(* qiangtai.rfid.controller..*.*(..))")
    public void controllerPointcut() {
        // 切点签名方法，不需要实现
    }
    @Before("controllerPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 如果不是 HTTP 调用（例如 Bean 创建阶段），直接跳过
        if (attributes == null) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();

        // 不记录 Knife4j/SpringDoc 内部接口
/*        if (request.getRequestURI().contains("/v3/api-docs") ||
                request.getRequestURI().contains("/swagger-resources") ||
                request.getRequestURI().contains("/doc.html")) {
            return;
        }*/
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();

        // 打印请求信息
        LOG.info("------------- 开始 -------------");
        LOG.info("请求地址: {} {}", request.getRequestURL().toString(), request.getMethod());
        LOG.info("类名方法: {}.{}", signature.getDeclaringTypeName(), name);
        LOG.info("远程地址: {}", request.getRemoteAddr());

        // 打印请求参数
        Object[] args = joinPoint.getArgs();
        // LOG.info("请求参数: {}", JSONObject.toJSONString(args));

        // 排除特殊类型的参数，如文件类型
        Object[] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest
                    || args[i] instanceof ServletResponse
                    || args[i] instanceof MultipartFile) {
                continue;
            }
            arguments[i] = args[i];
        }

        // 排除字段，敏感字段或太长的字段不显示：身份证、手机号、邮箱、密码等
        String[] excludeProperties = {};
        // 使用Spring Boot默认的Jackson ObjectMapper替代fastjson
        try {
           /* ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            // 创建过滤器排除特定属性
            if (excludeProperties.length > 0) {
                SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept(excludeProperties);
                FilterProvider filters = new SimpleFilterProvider().addFilter("propertyFilter", filter);
                objectMapper.setFilterProvider(filters);
            }*/
            // 使用 Spring 注入的 ObjectMapper（已含 JavaTimeModule）
            String json = objectMapper
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .writeValueAsString(arguments);
            LOG.info("请求参数: {}", json);
//            LOG.info("请求参数: {}", objectMapper.writeValueAsString(arguments));
        } catch (Exception e) {
            LOG.error("请求参数序列化失败: ", e);
        }
    }

    @Around("controllerPointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();

        // 排除字段，敏感字段或太长的字段不显示：身份证、手机号、邮箱、密码等
        try {
            LOG.warn("返回结果: {}", objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsString(result));
        } catch (Exception e) {
            LOG.error("返回结果序列化失败: ", e);
        }

        LOG.info("------------- 结束 耗时：{} ms -------------", System.currentTimeMillis() - startTime);
        return result;
    }
}