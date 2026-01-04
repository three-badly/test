package qiangtai.rfid.dto.rsp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserResultVO {
    /**
     * ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "用户ID")
    private Long id;

    /**
     * 身份[0- 1-]
     */
    @Schema(description = "身份")
    private String role;

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

    /**
     * 公司id
     */
    @Schema(description = "公司ID")
    private Integer companyId;

    /**
     * 登录token
     */
    @Schema(description = "登录token")
    private String token;

    /**
     * 公司名字
     */
    @Schema(description = "公司名字")
    private String companyName;
}
