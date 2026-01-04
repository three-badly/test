package qiangtai.rfid.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author FEI
 */
@Data
public class UserUpdatePasswordVO {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull(message = "用户id不能为空")
    @Schema(description = "用户ID")
    private Integer id;

    @NotEmpty(message = "旧密码不能为空")
    @Schema(description = "旧密码")
    private String oldPassword;

    @NotEmpty(message = "新密码不能为空")
    @Schema(description = "新密码")
    private String newPassword;
}
