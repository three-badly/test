package qiangtai.rfid.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author FEI
 */
@Data
public class DepartmentsUpdateVO {

    /**
     * 部门ID
     */
    @NotNull(message = "部门ID不能为空")
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
}
