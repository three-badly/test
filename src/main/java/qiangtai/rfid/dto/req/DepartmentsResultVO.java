package qiangtai.rfid.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DepartmentsResultVO {
    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    private Integer id;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String departmentName;

    /**
     * 部门编码
     */
    @Schema(description = "部门编码")
    private String deptCode;

    /**
     * 负责人
     */
    @Schema(description = "负责人")
    private String deptLeaderName;

    /**
     * 公司名称
     */
    @Schema(description = "公司名称")
    private String companyName;
}
