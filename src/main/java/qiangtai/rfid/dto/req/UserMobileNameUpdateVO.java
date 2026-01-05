package qiangtai.rfid.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author FEI
 */
@Data
public class UserMobileNameUpdateVO {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull(message = "用户id不能为空")
    @Schema(description = "用户ID")
    private Integer id;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String mobile;

    /**
     * 登录账号
     */
    @Schema(description = "登录账号")
    private String account;

    /**
     * 账号持有人名字
     */
    @Schema(description = "账号持有人名字")
    private String username;
}
