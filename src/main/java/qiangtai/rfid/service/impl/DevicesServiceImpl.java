package qiangtai.rfid.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import qiangtai.rfid.entity.Devices;
import qiangtai.rfid.service.DevicesService;
import qiangtai.rfid.mapper.DevicesMapper;
import org.springframework.stereotype.Service;

/**
* @author FEI
* @description 针对表【devices(门禁设备表)】的数据库操作Service实现
* @createDate 2025-12-24 12:44:25
*/
@Service
public class DevicesServiceImpl extends ServiceImpl<DevicesMapper, Devices>
    implements DevicesService{

}




