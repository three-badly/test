package qiangtai.rfid.dto.req;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginVO {

    /**
     * 登录账号
     */
    @NotEmpty
    @Schema(description = "登录账号")
    private String account;

    /**
     * 密码
     */
    @NotEmpty
    @Schema(description = "密码")
    private String password;

}
