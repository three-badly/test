package qiangtai.rfid.dto.req;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class EmployeesUpdateVO {
    /**
     * 员工id
     */
    @Schema(description = "员工ID")
    @NotNull(message = "员工ID不能为空")
    private String id;

    /**
     * 手机号 (机器读取的ID)
     */
    @Schema(description = "手机号")
    private String phoneNumber;

    /**
     * 员工姓名
     */
    @Schema(description = "员工姓名")
    private String name;

    /**
     * 所属部门ID
     */
    @Schema(description = "所属部门ID")
    private Integer departmentId;

    /**
     * 所属公司ID
     */
    @Schema(description = "所属公司ID")
    private Integer companyId;

    /**
     * 入职日期
     */
    @Schema(description = "入职日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    /**
     * 用工类型
     */
    @Schema(description = "用工类型")
    private String employeeType;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private String status;

    /**
     * 录入时间
     */
    @Schema(description = "录入时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 部门名字
     */
    @Schema(description = "部门名字")
    private String departmentName;

    /**
     * 公司名字
     */
    @Schema(description = "公司名字")
    private String companyName;

    /**
     * EPC号
     */
    @Schema(description = "EPC号")
    private String epc;

    /**
     * 职位
     */
    @TableField(value = "job_title")
    @Schema(description = "职位")
    private String jobTitle;

    /**
     * 工号
     */
    @TableField(value = "emp_no")
    @Schema(description = "工号")
    private String empNo;

    /**
     * 性别
     */
    @Schema(description = "性别")
    private String sex;

    /**
     * 年龄
     */
    @Schema(description = "年龄")
    private Integer age;
}
