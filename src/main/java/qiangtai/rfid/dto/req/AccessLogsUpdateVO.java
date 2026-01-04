package qiangtai.rfid.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author FEI
 */
@Data
public class AccessLogsUpdateVO {
    /**
     * 自增主键
     */
    @Schema(description = "自增主键")
    private Long id;

    /**
     * 员工手机号 (ID)
     */
    @Schema(description = "员工手机号")
    private String phoneNumber;

    /**
     * 设备id
     */
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
    @Schema(description = "出入方向：进入/离开")
    private String direction;

    /**
     * 员工刷卡时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
