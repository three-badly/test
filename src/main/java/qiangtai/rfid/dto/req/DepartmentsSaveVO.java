package qiangtai.rfid.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author FEI
 */
@Data
public class DepartmentsSaveVO {

    /**
     * 部门名称
     */
    @NotEmpty(message = "部门名称不能为空")
    @Schema(description = "部门名称")
    private String departmentName;

    /**
     * 所属公司ID
     */
    @Schema(description = "所属公司ID(后端自动填充前端不需要给值)")
    private Integer companyId;

    /**
     * 部门编码
     */
    @Schema(description = "部门编码")
    @NotEmpty(message = "部门编码不能为空")
    private String deptCode;

    /**
     * 负责人
     */
    @Schema(description = "负责人")
    @NotEmpty(message = "负责人不能为空")
    private String deptLeaderName;
}