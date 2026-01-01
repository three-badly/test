package qiangtai.rfid.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author FEI
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AccessLogsQuery extends PageRequest {
    /**
     * 自增主键
     */
    private Long id;

    /**
     * 员工手机号 (ID)
     */
    private String phoneNumber;

    /**
     * 设备id
     */
    private Integer machineId;

    /**
     * 打卡时员工姓名
     */
    private String employeeName;

    /**
     * 打卡时所属部门
     */
    private String deptName;

    /**
     * 打卡时所属公司
     */
    private String companyName;

    /**
     * 出入方向：进入/离开
     */
    private String direction;

    /**
     * 员工刷卡时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    /**
     * 打卡地址
     */
    private String location;
    /**
     * 公司id
     */
    private Integer companyId;
    /**
     * 部门id
     */
    private Integer deptId;
}
