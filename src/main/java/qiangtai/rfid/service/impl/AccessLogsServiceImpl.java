package qiangtai.rfid.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import qiangtai.rfid.entity.AccessLogs;
import qiangtai.rfid.service.AccessLogsService;
import qiangtai.rfid.mapper.AccessLogsMapper;
import org.springframework.stereotype.Service;

/**
* @author FEI
* @description 针对表【access_logs(门禁出入记录快照表)】的数据库操作Service实现
* @createDate 2025-12-26 14:27:34
*/
@Service
public class AccessLogsServiceImpl extends ServiceImpl<AccessLogsMapper, AccessLogs>
    implements AccessLogsService{

}




