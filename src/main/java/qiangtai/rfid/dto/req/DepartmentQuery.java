package qiangtai.rfid.dto.req;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author FEI
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class DepartmentQuery extends PageRequest {
    /**
     * 部门id
     */
    @TableId(type = IdType.AUTO)
    @Schema(description = "部门ID")
    private Integer id;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String departmentName;

    /**
     * 所属公司ID
     */
    @Schema(description = "所属公司ID")
    private Integer companyId;

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