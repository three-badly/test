package qiangtai.rfid.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import qiangtai.rfid.dto.req.DevicesQueryVO;
import qiangtai.rfid.dto.result.DevicesSaveVO;
import qiangtai.rfid.dto.result.Result;
import qiangtai.rfid.entity.Devices;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.validation.Valid;
import java.util.List;

/**
* @author FEI
* @description 针对表【devices(门禁设备表)】的数据库操作Service
* @createDate 2025-12-24 12:44:25
*/
public interface DevicesService extends IService<Devices> {

    List<Devices> listDevice();

    Page<Devices> pageDevice(DevicesQueryVO devicesQueryVO);

    Boolean add(DevicesSaveVO devicesSaveVO);

    Boolean deleteDevice(Integer id);
}
