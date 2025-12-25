package qiangtai.rfid.dto.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DepartmentsUpdateVO {

    /**
     * 部门ID
     */
    @NotNull(message = "部门ID不能为空")
    private Integer id;
    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 部门编码
     */
    private String deptCode;

    /**
     * 负责人
     */
    private String deptLeaderName;
}
