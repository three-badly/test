package qiangtai.rfid.dto.result;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "状态码")
    private Integer code;

    @Schema(description = "返回提示")
    private String message;

    @Schema(description = "是否成功")
    private Boolean success;

    @Schema(description = "返回数据")
    private T data;
    /* 快速工厂 */
    public static <T> Result<T> ok(T data) {
        return new Result<>(ResultCode.SUCCESS, "success",true, data);
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<>(ResultCode.ERROR, msg,false , null);
    }
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS, "success",true , data);
    }

    public static <T> Result<T> success(T data, String message) {
        return new Result<>(ResultCode.SUCCESS, message,true , data);
    }
    public static <T> Result<T> error(int code, String tip) {
        return new Result<>(code, tip,false, null);
    }
    public static <T> Result<T> error(String tip) {
        return new Result<>(ResultCode.ERROR, tip,false, null);
    }
}