package qiangtai.rfid.dto.rsp;

import lombok.Data;

@Data
public class CompanyNameId {

    /**
     * 公司编号
     */
    private Integer id;

    /**
     * 公司名称
     */
    private String companyName;
}
