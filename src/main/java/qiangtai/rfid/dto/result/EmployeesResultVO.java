package qiangtai.rfid.dto.result;

import lombok.Data;

import java.util.Date;

@Data
public class EmployeesResultVO {
    /**
     * 员工id转String防止精度丢失
     */
    private String id;

    /**
     * 手机号 (机器读取的ID)
     */
    private String phoneNumber;

    /**
     * 员工姓名
     */
    private String name;

    /**
     * 所属部门ID
     */
    private Integer departmentId;

    /**
     * 所属公司ID
     */
    private Integer companyId;

    /**
     * 入职日期
     */
    private Date hireDate;

    /**
     * 用工类型
     */
    private String employeeType;

    /**
     * 状态
     */
    private String status;

    /**
     * 录入时间
     */
    private Date createTime;
    /**
     * 部门名字
     */
    private String departmentName;
    /**
     * 公司名字
     */
    private String companyName;
}
