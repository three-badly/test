package qiangtai.rfid.dto.rsp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CompanyNameId {

    /**
     * 公司编号
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "公司编号")
    private Integer id;

    /**
     * 公司名称
     */
    @Schema(description = "公司名称")
    private String companyName;
}
