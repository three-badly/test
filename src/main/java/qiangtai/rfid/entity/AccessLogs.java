package qiangtai.rfid.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 门禁出入记录快照表
 * @TableName access_logs
 */
@TableName(value ="access_logs")
@Data
public class AccessLogs {
    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
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
    private Date timestamp;

    /**
     * 打卡地址
     */
    private String location;
}