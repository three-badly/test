package qiangtai.rfid.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import qiangtai.rfid.entity.AccessLogs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author FEI
* @description 针对表【access_logs(门禁出入记录快照表)】的数据库操作Mapper
* @createDate 2025-12-26 14:27:34
* @Entity qiangtai.rfid.entity.AccessLogs
*/
@Mapper
public interface AccessLogsMapper extends MPJBaseMapper<AccessLogs> {

}




