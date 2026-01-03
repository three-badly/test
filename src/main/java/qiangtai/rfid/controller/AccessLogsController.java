package qiangtai.rfid.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import qiangtai.rfid.context.UserContext;
import qiangtai.rfid.dto.req.AccessLogsQuery;
import qiangtai.rfid.dto.req.AccessLogsSaveVO;
import qiangtai.rfid.dto.req.AccessLogsUpdateVO;
import qiangtai.rfid.dto.result.Result;
import qiangtai.rfid.entity.AccessLogs;
import qiangtai.rfid.excel.req.AccessLogsExportQuery;
import qiangtai.rfid.service.AccessLogsService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author FEI
 */
@RequestMapping("/accessLogs")
@RestController
@RequiredArgsConstructor
@Tag(name = "进出记录接口")
public class AccessLogsController {
    private final AccessLogsService accessLogsService;

    @GetMapping("/pageAccessLogs")
    @Operation(summary = "进出日志多,分页查看进出日志")
    public Result<?> pageAccessLogs(@ParameterObject AccessLogsExportQuery accessLogsQuery) {
        return Result.success(accessLogsService.pageAccessLogs(accessLogsQuery));
    }

    @GetMapping("/listAccessLogs")
    @Operation(summary = "进出日志少,列表查看进出日志")
    public Result<?> listAccessLogs(@ParameterObject AccessLogsExportQuery qo) {
        return Result.success(accessLogsService.listAccessLogs(qo));
    }

    @PostMapping("/add")
    @Operation(summary = "新增进出日志(手动添加的数据)")
    public Result<?> add(@Valid @RequestBody AccessLogsSaveVO accessLogsSaveVO) {
        return Result.success(accessLogsService.add(accessLogsSaveVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新进出日志")
    public Result<?> updateAccessLogs(@Valid @RequestBody AccessLogsUpdateVO accessLogs) {
        return Result.success(accessLogsService.updateAccessLogs(accessLogs));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除进出日志")
    public Result<?> deleteAccessLogs(@PathVariable Integer id) {
        return Result.success(accessLogsService.deleteAccessLogs(id));
    }

    //导出进出日志excel
    @GetMapping("/export")
    @Operation(summary = "导出进出日志 Excel")
    public void exportAccessLogs(HttpServletResponse response, @Valid @ParameterObject AccessLogsExportQuery qo) {
        accessLogsService.export(response, qo);
    }
}
