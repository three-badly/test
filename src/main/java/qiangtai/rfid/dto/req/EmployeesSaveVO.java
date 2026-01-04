package qiangtai.rfid.dto.req;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author FEI
 */
@Schema(description = "员工保存参数")
@Data
public class EmployeesSaveVO {

    /**
     * 手机号 (机器读取的ID)
     */
    @Schema(description = "手机号")
    @NotEmpty(message = "请填写手机号")
    @Pattern(regexp = "^1\\d{10}$", message = "手机号格式不正确")
    private String phoneNumber;

    /**
     * 员工姓名
     */
    @NotEmpty(message = "请填写员工姓名")
    @Schema(description = "员工姓名")
    private String name;

    /**
     * 所属部门ID
     */
    @NotNull(message = "请填写部门ID")
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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "入职日期")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "录入时间")
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
