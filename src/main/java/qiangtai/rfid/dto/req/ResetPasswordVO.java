package qiangtai.rfid.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author FEI
 * 账号重置密码
 */
@Data
public class ResetPasswordVO {

    /**
     * 账号id
     */
    @NotNull(message = "账号id不能为空")
    @Schema(description = "账号ID")
    private Integer id;

    /**
     * 重置密码
     */
    @NotEmpty(message = "密码不能为空")
    @Schema(description = "重置密码")
    private String password;
}
