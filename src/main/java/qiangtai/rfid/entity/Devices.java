package qiangtai.rfid.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 门禁设备表
 * @TableName devices
 */
@TableName(value ="devices")
@Data
public class Devices implements Serializable {
    /**
     * 设备序列号/编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 设备名称 (如: 1号门进站口)
     */
    private String name;

    /**
     * 所属公司ID
     */
    private String companyId;

    /**
     * 安装位置
     */
    private String location;

    /**
     * 设备状态
     */
    private Object status;

    /**
     * 最后在线时间
     */
    private Date lastHeartbeat;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}