package qiangtai.rfid.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import qiangtai.rfid.dto.req.DevicesQueryVO;
import qiangtai.rfid.dto.req.DevicesSaveVO;
import qiangtai.rfid.dto.req.DevicesUpdateVO;
import qiangtai.rfid.entity.Devices;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.validation.Valid;
import java.util.List;

/**
* @author 16623
* @description 针对表【devices(门禁设备表)】的数据库操作Service
* @createDate 2026-01-08 14:03:31
*/
public interface DevicesService extends IService<Devices> {

    Page<Devices> pageDevice(@Valid DevicesQueryVO devicesQueryVO);

    Boolean add(@Valid DevicesSaveVO devicesSaveVO);

    Boolean updateDevice(@Valid DevicesUpdateVO devicesUpdateVO);

    List<Devices> listDevice();

    Boolean deleteDevice(Integer id);
}
