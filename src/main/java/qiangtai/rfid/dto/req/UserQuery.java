package qiangtai.rfid.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserQuery extends PageRequest {
    /**
     * 账号持有人名字模糊查询
     */
    @Schema(description = "账号持有人名字模糊查询")
    private String username;
    /**
     * 公司名称模糊查询
     */
    @Schema(description = "公司名称模糊查询")
    private String companyName;

}
