package qiangtai.rfid.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

/**
 * @author FEI
 */
@Data
public class AccessLogsSaveVO {

    /**
     * 员工手机号 (ID)
     */
    @NotEmpty(message = "员工手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    @Schema(description = "员工手机号")
    private String phoneNumber;

    /**
     * 设备id
     */
    @NotNull(message = "设备id不能为空")
    @Schema(description = "设备ID")
    private Integer machineId;

    /**
     * 打卡时员工姓名
     */
    @Schema(description = "打卡时员工姓名")
    private String employeeName;

    /**
     * 打卡时所属部门
     */
    @Schema(description = "打卡时所属部门")
    private String deptName;

    /**
     * 打卡时所属公司
     */
    @Schema(description = "打卡时所属公司")
    private String companyName;

    /**
     * 出入方向：进入/离开
     */
    @NotEmpty(message = "出入方向不能为空")
    @Schema(description = "出入方向：进入/离开")
    private String direction;

    /**
     * 员工刷卡时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "员工刷卡时间不能为空")
    @Schema(description = "员工刷卡时间")
    private LocalDateTime timestamp;

    /**
     * 打卡地址
     */
    @Schema(description = "打卡地址")
    private String location;

    /**
     * 公司id
     */
    @Schema(description = "公司ID")
    private Integer companyId;

    /**
     * 部门id
     */
    @Schema(description = "部门ID")
    private Integer deptId;
}
