package qiangtai.rfid.dto.req;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class DevicesUpdateVO {
    /**
     * 设备id
     */
    @Schema(description = "设备id")
    @NotNull(message = "设备id不能为空")
    private Integer id;

    /**
     * 设备编号
     */
    @Schema(description = "设备编号")
    private String deviceNo;

    /**
     * 设备类型
     */
    @Schema(description = "设备类型")
    private String deviceType;

    /**
     * 设备名称 (如: 1号门进站口)
     */
    @Schema(description = "设备名称")
    private String name;

    /**
     * 所属公司ID
     */
    @Schema(description = "所属公司ID")
    private Integer companyId;

    /**
     * 所在位置
     */
    @Schema(description = "所在位置")
    private String location;

    /**
     * 状态：正常/异常
     */
    @Schema(description = "状态：正常/异常")
    private String status;

    /**
     * 最后在线
     */
    @Schema(description = "最后在线时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime lastHeartbeat;
}
