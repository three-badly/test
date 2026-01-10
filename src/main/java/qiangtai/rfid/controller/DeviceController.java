package qiangtai.rfid.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rscja.deviceapi.ConnectionState;
import com.rscja.deviceapi.RFIDWithUHFNetworkA4;
import com.rscja.deviceapi.entity.AntennaNameEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import qiangtai.rfid.config.RfidAutoConfig;
import qiangtai.rfid.dto.req.DevicesQueryVO;
import qiangtai.rfid.dto.req.DevicesSaveVO;
import qiangtai.rfid.dto.req.DevicesUpdateVO;
import qiangtai.rfid.dto.result.Result;
import qiangtai.rfid.entity.Devices;
import qiangtai.rfid.service.DevicesService;


import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author FEI
 */
@RequestMapping("/device")
@RestController
@RequiredArgsConstructor
@Validated
@Tag(name = "设备管理接口")
@Slf4j
public class DeviceController {

    private final DevicesService devicesService;
    private final RFIDWithUHFNetworkA4 rfidInstance;
    private final RfidAutoConfig rfidConfig;


    @GetMapping("/pageDevice")
    @Operation(summary = "设备多,分页查看设备")
    public Result<Page<Devices>> pageDevice(@Valid @ParameterObject DevicesQueryVO devicesQueryVO) {
        return Result.success(devicesService.pageDevice(devicesQueryVO));
    }

    @PostMapping("/add")
    @Operation(summary = "新增设备")
    public Result<Boolean> add(@Valid @RequestBody DevicesSaveVO devicesSaveVO) {
        return Result.success(devicesService.add(devicesSaveVO));
    }

    @PutMapping("/update")
    @Operation(summary = "修改设备")
    public Result<Boolean> update(@Valid @RequestBody DevicesUpdateVO devicesUpdateVO) {
        return Result.success(devicesService.updateDevice(devicesUpdateVO), "设备修改成功");
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

    @Operation(summary = "启动RFID连接")
    @PostMapping("/connect")
    public Result<Boolean> connect() {
        try {
            // 检查是否已经连接
            // 注意：这里需要根据实际的API来判断连接状态，如果设备API不提供连接状态查询，
            // 可以先尝试断开再重新连接
            rfidInstance.free(); // 先释放可能存在的连接

            boolean connected = rfidInstance.init(rfidConfig.getHost(), rfidConfig.getPort());
            if (connected) {
                // 设置基础参数
                rfidInstance.setPower(AntennaNameEnum.valueOf(rfidConfig.getAntenna()), rfidConfig.getPower());
                // 设置回调并启动盘点
                rfidInstance.setInventoryCallback(rfidConfig::handleTag);
                rfidInstance.startInventoryTag();

                log.info("✅ RFID 连接已启动 {}:{}", rfidConfig.getHost(), rfidConfig.getPort());
                return Result.success(true, "RFID连接成功");
            } else {
                log.error("❌ RFID 连接失败 {}:{}", rfidConfig.getHost(), rfidConfig.getPort());
                return Result.error("RFID连接失败");
            }
        } catch (Exception e) {
            log.error("❌ RFID 连接异常", e);
            return Result.error("RFID连接异常: " + e.getMessage());
        }
    }

    @Operation(summary = "断开RFID连接")
    @PostMapping("/disconnect")
    public Result<Boolean> disconnect() {
        try {
            rfidInstance.stopInventory();
            rfidInstance.free();

            log.info("🔌 RFID 连接已断开 {}:{}", rfidConfig.getHost(), rfidConfig.getPort());
            return Result.success(true, "RFID断开连接成功");
        } catch (Exception e) {
            log.error("❌ RFID 断开连接异常", e);
            return Result.error("RFID断开连接异常: " + e.getMessage());
        }
    }

    @Operation(summary = "获取RFID连接状态")
    @GetMapping("/status")
    public Result<String> getStatus() {
        try {
            RFIDWithUHFNetworkA4 rfid = rfidConfig.rfidInstance();
            ConnectionState status = rfid.getConnectStatus();   // 你的枚举
            switch (status) {
                case CONNECTED:
                    return Result.success("已连接");
                case CONNTCTING:          // 如果以后改拼写记得一起换
                    return Result.success("连接中");
                case DISCONNECTED:
                    return Result.success("已断开");
                default:
                    return Result.success("未知状态");
            }
        } catch (Exception e) {
            log.error("获取 RFID 状态异常", e);
            return Result.success("连接异常");
        }
    }
}
