package qiangtai.rfid.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import qiangtai.rfid.dto.req.AccessLogsSaveVO;
import qiangtai.rfid.entity.AccessLogs;
import com.baomidou.mybatisplus.extension.service.IService;
import qiangtai.rfid.excel.req.AccessLogsExportQuery;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @author FEI
* @description 针对表【access_logs(门禁出入记录快照表)】的数据库操作Service
* @createDate 2025-12-26 14:27:34
*/
public interface AccessLogsService extends IService<AccessLogs> {

    Page<AccessLogs> pageAccessLogs(AccessLogsExportQuery accessLogsQuery);

    Boolean add(AccessLogsSaveVO accessLogsSaveVO);

    Boolean deleteAccessLogs(Integer id);

    void export(HttpServletResponse response, AccessLogsExportQuery qo);

    List<AccessLogs> listAccessLogs(AccessLogsExportQuery qo);
}
