package qiangtai.rfid.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Schema(description = "用户保存请求对象")
@Data
public class UserSaveVO {

    @Schema(description = "登录账号")
    @NotBlank(message = "登录账号不能为空")
    private String account;

    @Schema(description = "账号持有人名字")
    @NotBlank(message = "账号持有人名字不能为空")
    private String username;

    @Schema(description = "公司ID")
    @NotNull(message = "公司ID不能为空")
    private Integer companyId;

    @Schema(description = "手机号")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1\\d{10}$", message = "手机号格式不正确")
    private String mobile;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "角色")
    private String role;
}
