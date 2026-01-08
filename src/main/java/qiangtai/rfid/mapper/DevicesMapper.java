package qiangtai.rfid.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import qiangtai.rfid.entity.Devices;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 16623
* @description 针对表【devices(门禁设备表)】的数据库操作Mapper
* @createDate 2026-01-08 14:03:31
* @Entity qiangtai.rfid.entity.Devices
*/
@Mapper
public interface DevicesMapper extends MPJBaseMapper<Devices> {

}




