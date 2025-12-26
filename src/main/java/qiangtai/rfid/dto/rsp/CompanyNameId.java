package qiangtai.rfid.dto.rsp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class CompanyNameId {

    /**
     * 公司编号
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer id;

    /**
     * 公司名称
     */
    private String companyName;
}
