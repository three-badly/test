package qiangtai.rfid.dto.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class DepartmentsSaveVO {

    /**
     * 部门名称
     */
    @NotEmpty(message = "部门名称不能为空")
    private String departmentName;

    /**
     * 所属公司ID
     */
    private Integer companyId;

    /**
     * 部门编码
     */
    @NotEmpty(message = "部门编码不能为空")
    private String deptCode;

    /**
     * 负责人
     */
    @NotEmpty(message = "负责人不能为空")
    private String deptLeaderName;
}