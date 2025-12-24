package qiangtai.rfid.handler;



import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import qiangtai.rfid.dto.result.Result;
import qiangtai.rfid.utils.ResultUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

/**
 * 通用降级处理器
 * 提供默认的降级响应和异常处理
 * 
 * @author cwp
 * @date 2024-09-15
 */
@Component
@Slf4j
public class FallbackHandler {

    /**
     * 通用降级方法 - 无参数
     */
    public Result<?> defaultFallback() {
        log.warn("触发默认降级方法");
        return ResultUtil.error(500, "服务暂时不可用，请稍后重试");
    }

    /**
     * 通用降级方法 - 带异常信息
     */
    public Result<?> defaultFallback(Throwable throwable) {
        String message = getErrorMessage(throwable);
        log.warn("触发默认降级方法，异常: {}", message);
        return ResultUtil.error(500, message);
    }

    /**
     * Feign调用降级方法
     */
    public Result<?> feignFallback(Throwable throwable) {
        String message = getFeignErrorMessage(throwable);
        log.error("Feign调用失败，触发降级: {}", message, throwable);
        return ResultUtil.error(503, message);
    }

    /**
     * 数据库操作降级方法
     */
    public Result<?> databaseFallback(Throwable throwable) {
        log.error("数据库操作失败，触发降级: {}", throwable.getMessage(), throwable);
        return ResultUtil.error(500, "数据服务暂时不可用，请稍后重试");
    }

    /**
     * 缓存操作降级方法
     */
    public Result<?> cacheFallback(Throwable throwable) {
        log.warn("缓存操作失败，触发降级: {}", throwable.getMessage());
        return ResultUtil.error(500, "缓存服务暂时不可用，正在使用数据库查询");
    }

    /**
     * 消息队列操作降级方法
     */
    public Result<?> messageFallback(Throwable throwable) {
        log.error("消息队列操作失败，触发降级: {}", throwable.getMessage(), throwable);
        return ResultUtil.error(500, "消息服务暂时不可用，请稍后重试");
    }

    /**
     * 文件操作降级方法
     */
    public Result<?> fileFallback(Throwable throwable) {
        log.error("文件操作失败，触发降级: {}", throwable.getMessage(), throwable);
        return ResultUtil.error(500, "文件服务暂时不可用，请稍后重试");
    }

    /**
     * 根据异常类型获取错误信息
     */
    private String getErrorMessage(Throwable throwable) {
        if (throwable instanceof CallNotPermittedException) {
            return "服务熔断中，请稍后重试";
        } else if (throwable instanceof RequestNotPermitted) {
            return "请求频率过高，请稍后重试";
        } else if (throwable instanceof TimeoutException || throwable instanceof SocketTimeoutException) {
            return "服务响应超时，请稍后重试";
        } else if (throwable instanceof ConnectException) {
            return "服务连接失败，请稍后重试";
        } else if (throwable instanceof IllegalArgumentException) {
            return "请求参数错误: " + throwable.getMessage();
        } else {
            return "服务暂时不可用，请稍后重试";
        }
    }

    /**
     * 根据Feign异常类型获取错误信息
     */
    private String getFeignErrorMessage(Throwable throwable) {
        if (throwable instanceof CallNotPermittedException) {
            return "远程服务熔断中，请稍后重试";
        } else if (throwable instanceof RequestNotPermitted) {
            return "远程服务请求频率过高，请稍后重试";
        } else if (throwable instanceof TimeoutException || throwable instanceof SocketTimeoutException) {
            return "远程服务响应超时，请稍后重试";
        } else if (throwable instanceof ConnectException) {
            return "远程服务连接失败，请稍后重试";
        } else {
            return "远程服务暂时不可用，请稍后重试";
        }
    }

    /**
     * 降级响应构建器
     */
    public static class FallbackResponseBuilder {
        private int code = 500;
        private String message = "服务暂时不可用";
        private Object data = null;

        public static FallbackResponseBuilder builder() {
            return new FallbackResponseBuilder();
        }

        public FallbackResponseBuilder code(int code) {
            this.code = code;
            return this;
        }

        public FallbackResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public FallbackResponseBuilder data(Object data) {
            this.data = data;
            return this;
        }

        public Result<?> build() {
            return ResultUtil.error(code, message, data);
        }
    }

    /**
     * 创建自定义降级响应
     */
    public Result<?> customFallback(int code, String message) {
        return ResultUtil.error(code, message);
    }

    /**
     * 创建自定义降级响应（带数据）
     */
    public Result<?> customFallback(int code, String message, Object data) {
        return ResultUtil.error(code, message, data);
    }

    /**
     * 创建成功的降级响应（使用缓存数据等）
     */
    public Result<?> successFallback(Object data) {
        return ResultUtil.success(data);
    }

    /**
     * 创建成功的降级响应（带提示信息）
     */
    public Result<?> successFallback(Object data, String message) {
        Result<?> result = ResultUtil.success(data);
        result.setMessage(message);
        return result;
    }
}