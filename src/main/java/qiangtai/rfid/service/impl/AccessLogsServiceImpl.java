package qiangtai.rfid.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import qiangtai.rfid.context.UserContext;
import qiangtai.rfid.dto.req.AccessLogsSaveVO;
import qiangtai.rfid.dto.req.AccessLogsUpdateVO;
import qiangtai.rfid.dto.rsp.AccessLogsResultVO;
import qiangtai.rfid.entity.AccessLogs;
import qiangtai.rfid.entity.Devices;
import qiangtai.rfid.entity.Employees;
import qiangtai.rfid.excel.exportVO.AccessLogsExportExcel;
import qiangtai.rfid.excel.req.AccessLogsExportQuery;
import qiangtai.rfid.handler.exception.BusinessException;
import qiangtai.rfid.mapper.*;
import qiangtai.rfid.service.AccessLogsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author FEI
 * @description 针对表【access_logs(门禁出入记录快照表)】的数据库操作Service实现
 * @createDate 2025-12-26 14:27:34
 */
@Service
public class AccessLogsServiceImpl extends ServiceImpl<AccessLogsMapper, AccessLogs>
        implements AccessLogsService {

    private final EmployeesMapper employeesMapper;
    private final DevicesMapper devicesMapper;
    private final AccessLogsMapper accessLogsMapper;

    public AccessLogsServiceImpl(EmployeesMapper employeesMapper, DevicesMapper devicesMapper, AccessLogsMapper accessLogsMapper) {
        this.employeesMapper = employeesMapper;
        this.devicesMapper = devicesMapper;
        this.accessLogsMapper = accessLogsMapper;
    }

    @Override
    public Page<AccessLogsResultVO> pageAccessLogs(AccessLogsExportQuery qo) {
        Page<AccessLogsResultVO> page = new Page<>(qo.getCurrent(), qo.getSize());
        //平台看全部
        return lambdaQueryCondition(qo).page(page, AccessLogsResultVO.class);
    }

    /**
     * 分页，列表条件公用
     *
     * @param qo
     */
    public MPJLambdaWrapper<AccessLogs> lambdaQueryCondition(AccessLogsExportQuery qo) {
        return new MPJLambdaWrapper<>(AccessLogs.class)
                .selectAll()
                .select(Employees::getEpc, Employees::getEmpNo)
                .leftJoin(Employees.class, Employees::getPhoneNumber, AccessLogs::getPhoneNumber)
                .like(StrUtil.isNotBlank(qo.getPhoneNumber()), AccessLogs::getPhoneNumber, qo.getPhoneNumber())
                .like(StrUtil.isNotBlank(qo.getDeptName()), AccessLogs::getDeptName, qo.getDeptName())
                .between(AccessLogs::getTimestamp, qo.getStartTime(), qo.getEndTime())
                //管理员可查看所有日志
                .eq(UserContext.get().getCompanyId() != -1, AccessLogs::getCompanyId, UserContext.get().getCompanyId());
    }

    @Override
    public Boolean add(AccessLogsSaveVO accessLogsSaveVO) {
        Employees employees = employeesMapper.selectOne(Wrappers.<Employees>lambdaQuery()
                .eq(Employees::getPhoneNumber, accessLogsSaveVO.getPhoneNumber())
                .eq(UserContext.get().getCompanyId() != -1, Employees::getCompanyId, UserContext.get().getCompanyId())
        );
        if (employees == null) {
            throw new BusinessException(400, "手机号不存在员工表");
        }
        accessLogsSaveVO.setEmployeeName(employees.getName());
        accessLogsSaveVO.setCompanyId(employees.getCompanyId());
        accessLogsSaveVO.setDeptId(employees.getDepartmentId());
        accessLogsSaveVO.setCompanyName(employees.getCompanyName());
        accessLogsSaveVO.setDeptName(employees.getDepartmentName());
        Devices devices = devicesMapper.selectOne(Wrappers.<Devices>lambdaQuery()
                .eq(Devices::getId, accessLogsSaveVO.getMachineId())
                .eq(UserContext.get().getCompanyId() != -1, Devices::getCompanyId, UserContext.get().getCompanyId())
        );
        if (devices == null) {
            throw new BusinessException(400, "设备不存在");
        }
        accessLogsSaveVO.setLocation(devices.getLocation());
        AccessLogs accessLogs = BeanUtil.copyProperties(accessLogsSaveVO, AccessLogs.class);
        return this.save(accessLogs);
    }

    @Override
    public Boolean deleteAccessLogs(Integer id) {
        Integer companyId = UserContext.get().getCompanyId();

        boolean remove = this.remove(Wrappers.<AccessLogs>lambdaQuery().eq(AccessLogs::getId, id).eq(companyId != -1, AccessLogs::getCompanyId, companyId));
        if (!remove) {
            throw new BusinessException(400, "无权限删除日志记录");
        }
        return true;
    }

    @Override
    public void export(HttpServletResponse response, AccessLogsExportQuery qo) {
        // 1. 查当前公司数据
        List<AccessLogsResultVO> list = listAccessLogs(qo);
        if (CollUtil.isEmpty(list)) {
            throw new BusinessException(10035, "未查询到可导出数据");
        }

        // 2. 转 Excel 模型
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<AccessLogsExportExcel> accessLogsExportExcels = BeanUtil.copyToList(list, AccessLogsExportExcel.class);
        accessLogsExportExcels.forEach(ex -> {
            // 从源对象里再取 LocalDateTime 来格式化
            ex.setTimestamp(
                    list.get(accessLogsExportExcels.indexOf(ex)).getTimestamp().format(formatter));
        });

        // 3. 写响应流
        try {
            String fileName = URLEncoder.encode("进出记录_" + DateUtil.today(), "UTF-8");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            EasyExcel.write(response.getOutputStream(), AccessLogsExportExcel.class)
                    .sheet("进出记录")
                    .doWrite(accessLogsExportExcels);
        } catch (IOException e) {
            throw new BusinessException(10034, "导出失败：" + e.getMessage());
        }
    }

    @Override
    public List<AccessLogsResultVO> listAccessLogs(AccessLogsExportQuery qo) {
        //管理员可查看所有日志
        return lambdaQueryCondition(qo).list(AccessLogsResultVO.class);
    }

    @Override
    public Boolean updateAccessLogs(AccessLogsUpdateVO accessLogs) {
        AccessLogs accessLogs1 = BeanUtil.copyProperties(accessLogs, AccessLogs.class);
        //todo 更新业务逻辑校验

        return this.updateById(accessLogs1);
    }
}




