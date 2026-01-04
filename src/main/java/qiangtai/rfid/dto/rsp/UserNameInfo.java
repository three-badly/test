package qiangtai.rfid.dto.rsp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserNameInfo {
    /**
     * 公司名称
     */
    @Schema(description = "公司名称")
    private String companyName;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String account;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;

    @Schema(description = "公司ID")
    private Integer companyId;
}
