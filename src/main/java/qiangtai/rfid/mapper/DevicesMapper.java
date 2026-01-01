package qiangtai.rfid.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import qiangtai.rfid.entity.Devices;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author FEI
* @description 针对表【devices(门禁设备表)】的数据库操作Mapper
* @createDate 2025-12-24 12:44:25
* @Entity qiangtai.rfid.entity.Devices
*/
@Mapper
public interface DevicesMapper extends MPJBaseMapper<Devices> {

}




