package qiangtai.rfid.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Schema(description = "用户保存请求对象")
@Data
public class UserSaveVO {

    @Schema(description = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    private String username;



    @Schema(description = "公司ID", required = true)
    @NotBlank(message = "公司ID不能为空")
    private String companyId;

    @Schema(description = "身份")
    private String identity;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "密码过期时间")
    private LocalDateTime passwordExpireTime;
}
