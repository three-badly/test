package qiangtai.rfid.excel.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import qiangtai.rfid.dto.req.PageRequest;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author FEI
 * @description 出入日志分页参数
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AccessLogsExportQuery extends PageRequest {

    @Schema(description = "员工手机号，留空则查全部")
    private String phoneNumber;

    @Schema(description = "开始时间", example = "2025-01-01 00:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "开始时间不能为空")
    private LocalDateTime startTime;

    @Schema(description = "结束时间", example = "2025-12-31 23:59:59")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "结束时间不能为空")
    private LocalDateTime endTime;

    @Schema(description = "部门名称，留空则查全部")
    private String deptName;

}