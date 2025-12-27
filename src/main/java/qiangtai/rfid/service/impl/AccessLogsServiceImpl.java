package qiangtai.rfid.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import qiangtai.rfid.context.UserContext;
import qiangtai.rfid.dto.req.AccessLogsQuery;
import qiangtai.rfid.dto.req.AccessLogsSaveVO;
import qiangtai.rfid.entity.AccessLogs;
import qiangtai.rfid.entity.Devices;
import qiangtai.rfid.entity.Employees;
import qiangtai.rfid.entity.User;
import qiangtai.rfid.handler.exception.BusinessException;
import qiangtai.rfid.mapper.*;
import qiangtai.rfid.service.AccessLogsService;
import org.springframework.stereotype.Service;

/**
* @author FEI
* @description 针对表【access_logs(门禁出入记录快照表)】的数据库操作Service实现
* @createDate 2025-12-26 14:27:34
*/
@Service
public class AccessLogsServiceImpl extends ServiceImpl<AccessLogsMapper, AccessLogs>
    implements AccessLogsService{

    private final DepartmentsMapper departmentsMapper;
    private final LoginMapper loginMapper;
    private final EmployeesMapper employeesMapper;
    private final DevicesMapper devicesMapper;

    public AccessLogsServiceImpl(DepartmentsMapper departmentsMapper, LoginMapper loginMapper, EmployeesMapper employeesMapper, DevicesMapper devicesMapper) {
        this.departmentsMapper = departmentsMapper;
        this.loginMapper = loginMapper;
        this.employeesMapper = employeesMapper;
        this.devicesMapper = devicesMapper;
    }

    @Override
    public Page<AccessLogs> pageAccessLogs(AccessLogsQuery accessLogsQuery) {
        Page<AccessLogs> page = new Page<>(accessLogsQuery.getCurrent(), accessLogsQuery.getSize());
        Integer companyId = UserContext.get().getCompanyId();
        //平台看全部
        return this.page(page, Wrappers.<AccessLogs>lambdaQuery()
                .eq(companyId != -1, AccessLogs::getCompanyId, companyId));
    }

    @Override
    public Boolean add(AccessLogsSaveVO accessLogsSaveVO) {
        Employees employees = employeesMapper.selectOne(Wrappers.<Employees>lambdaQuery()
                .eq(Employees::getPhoneNumber, accessLogsSaveVO.getPhoneNumber())
                //todo系统管理员是否有权限？
                .eq(UserContext.get().getCompanyId()!= -1,Employees::getCompanyId, UserContext.get().getCompanyId())
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
        if (devices == null){
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
}




