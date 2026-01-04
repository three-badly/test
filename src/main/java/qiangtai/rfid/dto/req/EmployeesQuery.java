package qiangtai.rfid.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author FEI
 */
@Data
public class EmployeesQuery extends PageRequest {
    /**
     * 员工id
     */
    @Schema(description = "员工ID")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
}
