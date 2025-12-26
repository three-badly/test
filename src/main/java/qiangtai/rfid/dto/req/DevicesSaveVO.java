package qiangtai.rfid.dto.req;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 门禁设备表
 * @TableName devices
 */
@Data
public class DevicesSaveVO {
    /**
     * 设备序列号/编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 设备名称 (如: 1号门进站口)
     */
    @NotEmpty(message = "设备名称不能为空")
    private String name;

    /**
     * 所属公司ID
     */
    @NotNull(message = "所属公司ID不能为空")
    private Integer companyId;

    /**
     * 安装位置
     */
    @NotEmpty(message = "安装位置不能为空")
    private String location;

    /**
     * 设备状态
     */
    private String status;

    /**
     * 最后在线时间
     */
    private Date lastHeartbeat;


}