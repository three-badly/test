package qiangtai.rfid.dto.req;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CompanyQuery extends PageRequest {
    /**
     * 公司名称模糊查询
     */
    private String companyName;
}
