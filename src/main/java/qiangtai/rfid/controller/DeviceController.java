package qiangtai.rfid.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import qiangtai.rfid.context.UserContext;
import qiangtai.rfid.dto.req.DevicesQueryVO;
import qiangtai.rfid.dto.req.DevicesSaveVO;
import qiangtai.rfid.dto.result.Result;
import qiangtai.rfid.entity.Devices;
import qiangtai.rfid.service.DevicesService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author FEI
 */
@RequestMapping("/device")
@RestController
@RequiredArgsConstructor
@Validated
@Tag(name = "设备管理接口")
public class DeviceController {

    private final DevicesService devicesService;

    @GetMapping("/pageDevice")
    @Operation(summary = "设备多,分页查看设备")
    public Result<Page<Devices>> pageDevice(@Valid @ParameterObject DevicesQueryVO devicesQueryVO) {
        return Result.success(devicesService.pageDevice(devicesQueryVO));
    }

    @PostMapping("/add")
    @Operation(summary = "新增设备")
    public Result<Boolean> add(@Valid  @RequestBody DevicesSaveVO devicesSaveVO) {
        return Result.success(devicesService.add(devicesSaveVO));
    }

    @GetMapping("/listDevice")
    @Operation(summary = "设备少,列表查看设备")
    public Result<List<Devices>> listDevice() {
        return Result.success(devicesService.listDevice());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除设备")
    public Result<Boolean> deleteDevice(@PathVariable Integer id) {
        return Result.success(devicesService.deleteDevice(id), "删除成功");
    }
}
