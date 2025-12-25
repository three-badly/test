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

    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String account;

    @Schema(description = "公司ID")
    @NotNull(message = "公司ID不能为空")
    private Integer companyId;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "角色")
    private String role;
}
