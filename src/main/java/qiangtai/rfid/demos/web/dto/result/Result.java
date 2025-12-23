package qiangtai.rfid.demos.web.dto.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cwp
 * @date 2024-01-09 12:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    
    private Integer code;
    
    private String message;
    
    private T data;
    /* 快速工厂 */
    public static <T> Result<T> ok(T data) {
        return new Result<>(ResultCode.SUCCESS, "success", data);
    }
    public static <T> Result<T> ok() { return ok(null); }

    public static <T> Result<T> fail(String msg) {
        return new Result<>(ResultCode.ERROR, msg, null);
    }
}