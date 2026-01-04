package qiangtai.rfid.dto.req;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 门禁设备表
 * @author FEI
 * @TableName devices
 */
@Data
public class DevicesSaveVO {
    /**
     * 设备序列号/编号
     */
    @TableId(type = IdType.AUTO)
    @Schema(description = "设备ID")
    private Integer id;

    /**
     * 设备名称 (如: 1号门进站口)
     */
    @NotEmpty(message = "设备名称不能为空")
    @Schema(description = "设备名称")
    private String name;

    /**
     * 所属公司ID
     */
    @NotNull(message = "所属公司ID不能为空")
    @Schema(description = "所属公司ID")
    private Integer companyId;

    /**
     * 安装位置
     */
    @NotEmpty(message = "安装位置不能为空")
    @Schema(description = "安装位置")
    private String location;

    /**
     * 设备状态
     */
    @Schema(description = "设备状态")
    private String status;

    /**
     * 最后在线时间
     */
    @Schema(description = "最后在线时间")
    private Date lastHeartbeat;
}
