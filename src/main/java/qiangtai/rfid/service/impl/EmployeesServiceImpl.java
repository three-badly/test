package qiangtai.rfid.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import qiangtai.rfid.context.UserContext;
import qiangtai.rfid.dto.req.EmployeesQuery;
import qiangtai.rfid.dto.req.EmployeesSaveVO;
import qiangtai.rfid.dto.result.Result;
import qiangtai.rfid.dto.rsp.EmployeesResultVO;
import qiangtai.rfid.entity.Company;
import qiangtai.rfid.entity.Departments;
import qiangtai.rfid.entity.Employees;
import qiangtai.rfid.handler.exception.BusinessException;
import qiangtai.rfid.mapper.CompanyMapper;
import qiangtai.rfid.mapper.DepartmentsMapper;
import qiangtai.rfid.service.EmployeesService;
import qiangtai.rfid.mapper.EmployeesMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author FEI
 * @description 针对表【employees(员工信息表)】的数据库操作Service实现
 * @createDate 2025-12-25 18:15:02
 */
@Service
public class EmployeesServiceImpl extends ServiceImpl<EmployeesMapper, Employees>
        implements EmployeesService {

    private final DepartmentsMapper departmentsMapper;
    private final CompanyMapper companyMapper;
    private final EmployeesMapper employeesMapper;

    public EmployeesServiceImpl(DepartmentsMapper departmentsMapper, CompanyMapper companyMapper, EmployeesMapper employeesMapper) {
        this.departmentsMapper = departmentsMapper;
        this.companyMapper = companyMapper;
        this.employeesMapper = employeesMapper;
    }

    @Override
    public Boolean add(EmployeesSaveVO employeesSaveVO) {
        Employees employees = BeanUtil.copyProperties(employeesSaveVO, Employees.class);
        Integer companyId = UserContext.get().getCompanyId();
        employees.setCompanyId(companyId);
        String departmentName = departmentsMapper.selectById(employees.getDepartmentId()).getDepartmentName();
        employees.setDepartmentName(departmentName);
        String companyName = companyMapper.selectById(companyId).getCompanyName();
        employees.setCompanyName(companyName);
        return this.save(employees);
    }

    @Override
    public Boolean removeEmployeeById(String id) {
        //字符串 转 Long
        Long realId = Long.parseLong(id);
        boolean remove = this.remove(Wrappers.<Employees>lambdaQuery()
                .eq(Employees::getId, realId)
                //todo系统管理员是否有权限？
                .eq(UserContext.get().getCompanyId() != -1, Employees::getCompanyId, UserContext.get().getCompanyId()));
        if (!remove) {
            throw new BusinessException(10023, "当前公司员工不存在");
        }
        return true;

    }

    @Override
    public Page<EmployeesResultVO> pageEmployees(EmployeesQuery employeesQuery) {
        Page<Employees> page = new Page<>(employeesQuery.getCurrent(), employeesQuery.getSize());
        LambdaQueryWrapper<Employees> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StringUtils.hasText(employeesQuery.getName()), Employees::getName, employeesQuery.getName())
                .like(StringUtils.hasText(employeesQuery.getPhoneNumber()),
                        Employees::getPhoneNumber, employeesQuery.getPhoneNumber())
                .eq(employeesQuery.getDepartmentId() != null,
                        Employees::getDepartmentId, employeesQuery.getDepartmentId())
                // 本公司 todo系统管理员是否有权限？
                .eq(UserContext.get().getCompanyId() != -1,
                        Employees::getCompanyId, UserContext.get().getCompanyId());
        Page<Employees> page1 = this.page(page, wrapper);
        Page<EmployeesResultVO> page2 = new Page<>();
        BeanUtil.copyProperties(page1, page2);
        //封装回字符串型态防止前端精度丢失
        List<EmployeesResultVO> employeesResultVOS = BeanUtil.copyToList(page1.getRecords(), EmployeesResultVO.class);
        page2.setRecords(employeesResultVOS);
        return page2;

    }

    @Override
    public Boolean updateEmployees(Employees employees1) {
        Employees employees = this.getOne(Wrappers.<Employees>lambdaQuery()
                .eq(Employees::getId, employees1.getId())
                //todo系统管理员是否有权限？
                .eq(Employees::getCompanyId, UserContext.get().getCompanyId()));
        if (employees == null) {
            throw new BusinessException(10023, "当前公司员工不存在");
        }
        //只要部门id更新，立即更新冗余字段部门名字
        if (!Objects.equals(employees1.getDepartmentId(), employees.getDepartmentId())) {
            //数据库提取部门名字
            String departmentName = departmentsMapper.selectById(employees.getDepartmentId()).getDepartmentName();
            employees1.setDepartmentName(departmentName);
        }

        return this.updateById(employees1);
    }

    /**
     * EasyExcel 导入
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> importExcel(List<Employees> list) {
        if (CollUtil.isEmpty(list)) {
            return Result.fail("导入数据为空");
        }
        //赋值部门id
        Map<String, Integer> departNames = departmentsMapper.selectList(Wrappers.<Departments>lambdaQuery()
                .eq(Departments::getCompanyId, UserContext.get().getCompanyId())
        ).stream().collect(Collectors.toMap(Departments::getDepartmentName, Departments::getId));

        list.forEach(employees -> {
            Integer departmentId = departNames.get(employees.getDepartmentName());
            if (departmentId == null) {
                throw new BusinessException(10023, "部门不存在");
            }
            employees.setDepartmentId(departmentId);
        });
        // 1. 按需做重复校验（例如手机号）
        List<String> phones = list.stream().map(Employees::getPhoneNumber).collect(Collectors.toList());
        List<Employees> exist = employeesMapper.selectList(Wrappers.<Employees>lambdaQuery()
                .eq(Employees::getCompanyId, UserContext.get().getCompanyId())
                .in(Employees::getPhoneNumber, phones));
        if (CollUtil.isNotEmpty(exist)) {
            List<String> repeat = exist.stream().map(Employees::getPhoneNumber).collect(Collectors.toList());
            return Result.fail("以下手机号已存在：" + CollUtil.join(repeat, ","));
        }
        // 2. 批量写入
        boolean ok = saveBatch(list);
        return ok ? Result.success(true) : Result.fail("导入失败");
    }
}




