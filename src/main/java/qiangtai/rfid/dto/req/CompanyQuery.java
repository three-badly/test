package qiangtai.rfid.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author FEI
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class CompanyQuery extends PageRequest {
    /**
     * 公司名称模糊查询
     */
    @Schema(description = "公司名称模糊查询")
    private String companyName;
}
