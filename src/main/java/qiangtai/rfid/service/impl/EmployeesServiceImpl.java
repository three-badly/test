package qiangtai.rfid.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.transaction.annotation.Transactional;

import qiangtai.rfid.context.UserContext;
import qiangtai.rfid.dto.req.EmployeesQuery;
import qiangtai.rfid.dto.req.EmployeesSaveVO;
import qiangtai.rfid.dto.req.UserQuery;
import qiangtai.rfid.dto.result.Result;
import qiangtai.rfid.dto.rsp.EmployeesResultVO;
import qiangtai.rfid.entity.Company;
import qiangtai.rfid.entity.Departments;
import qiangtai.rfid.entity.Employees;
import qiangtai.rfid.entity.User;
import qiangtai.rfid.handler.exception.BusinessException;
import qiangtai.rfid.mapper.CompanyMapper;
import qiangtai.rfid.mapper.DepartmentsMapper;
import qiangtai.rfid.service.EmployeesService;
import qiangtai.rfid.mapper.EmployeesMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
                .eq(UserContext.get().getCompanyId() != -1, Employees::getCompanyId, UserContext.get().getCompanyId()));
        if (!remove) {
            throw new BusinessException(10023, "当前公司员工不存在");
        }
        return true;

    }

    @Override
    public Page<EmployeesResultVO> pageEmployees(EmployeesQuery employeesQuery) {
        Page<EmployeesResultVO> page = new Page<>(employeesQuery.getCurrent(), employeesQuery.getSize());

        return getWrapper(employeesQuery).page(page, EmployeesResultVO.class);

    }

    public MPJLambdaWrapper<Employees> getWrapper(EmployeesQuery employeesQuery) {
        Integer companyId = UserContext.get().getCompanyId();
        return new MPJLambdaWrapper<>(Employees.class)
                .eq(employeesQuery.getDepartmentId() != null,
                        Employees::getDepartmentId, employeesQuery.getDepartmentId())
                .eq(companyId != -1,
                        Employees::getCompanyId, companyId)
                .eq(employeesQuery.getDepartmentId() != null, Employees::getDepartmentId, employeesQuery.getDepartmentId())
                .eq(StrUtil.isNotBlank(employeesQuery.getStatus()), Employees::getStatus, employeesQuery.getStatus())
                .and(StringUtils.isNotBlank(employeesQuery.getName()),
                        w -> w.like(Employees::getName, employeesQuery.getName())
                                .or().like(Employees::getPhoneNumber, employeesQuery.getName())
                                .or().like(Employees::getEmpNo, employeesQuery.getName())
                        // 后续还可加模糊查询
                )
                // 排序（可选，按 id 倒序）
                .orderByDesc(User::getId);

    }

    @Override
    public Boolean updateEmployees(Employees employees1) {
        Employees employees = this.getOne(Wrappers.<Employees>lambdaQuery()
                .eq(Employees::getId, employees1.getId())
                .eq(UserContext.get().getCompanyId() != -1, Employees::getCompanyId, UserContext.get().getCompanyId()));
        if (employees == null) {
            throw new BusinessException(10023, "当前公司员工不存在");
        }
        //此处前端传的事部门名字，立即更新部门id和冗余字段部门名字
        if (StrUtil.isNotBlank(employees1.getDepartmentName())) {
            if (employees.getDepartmentName().equals(employees1.getDepartmentName())) {
                Integer id = departmentsMapper.selectOne(Wrappers.<Departments>lambdaQuery()
                        .eq(Departments::getDepartmentName, employees1.getDepartmentName())
                        .eq(UserContext.get().getCompanyId() != -1,
                                Departments::getCompanyId, UserContext.get().getCompanyId())

                ).getId();
                employees1.setDepartmentId(id);
            }
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
        Integer companyId = UserContext.get().getCompanyId();
        String companyName = UserContext.get().getCompanyName();
        if (companyId == -1) {
            throw new BusinessException(10023, "请用公司对应的账号来导入员工");
        }
        //赋值部门id
        Map<String, Integer> departNames = departmentsMapper.selectList(Wrappers.<Departments>lambdaQuery()
                .eq(Departments::getCompanyId, companyId)
        ).stream().collect(Collectors.toMap(Departments::getDepartmentName, Departments::getId));

        list.forEach(employees -> {
            Integer departmentId = departNames.get(employees.getDepartmentName());
            if (departmentId == null) {
                throw new BusinessException(10023, employees.getDepartmentName() + " 部门不存在，请先确认公司下是否有该部门");
            }
            employees.setDepartmentId(departmentId);
            employees.setCompanyId(companyId);
            employees.setCompanyName(companyName);
        });
        // 1. 按需做重复校验（例如手机号
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
        return ok ? Result.success(true, "导入成功，记录总数:" + list.size()) : Result.fail("导入失败");
    }

    @Override
    public List<EmployeesResultVO> listEmployees(EmployeesQuery employeesQuery) {
        return getWrapper(employeesQuery).list(EmployeesResultVO.class);
    }
}




