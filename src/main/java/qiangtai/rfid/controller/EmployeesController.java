package qiangtai.rfid.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import qiangtai.rfid.context.UserContext;
import qiangtai.rfid.dto.req.EmployeesQuery;
import qiangtai.rfid.dto.req.EmployeesSaveVO;
import qiangtai.rfid.dto.req.EmployeesUpdateVO;
import qiangtai.rfid.dto.result.EmployeesResultVO;
import qiangtai.rfid.dto.result.Result;
import qiangtai.rfid.entity.Employees;
import qiangtai.rfid.service.EmployeesService;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/employees")
@RestController
@RequiredArgsConstructor
@Validated
@Tag(name = "员工接口")
public class EmployeesController {
    private final EmployeesService employeesService;
    @GetMapping("/pageEmployees")
    @Operation(summary = "员工多,分页查看员工")
    public Result<?> pageEmployees(EmployeesQuery employeesQuery) {
        return Result.success(employeesService.pageEmployees(employeesQuery));
    }
    @GetMapping("/listEmployees")
    @Operation(summary = "员工少,列表查看员工")
    public Result<?> listEmployees() {
        Integer companyId = UserContext.get().getCompanyId();
        //todo系统管理员是否有权限？
        List<Employees> list = employeesService.list(Wrappers.<Employees>lambdaQuery()
                .eq(UserContext.get().getCompanyId() != -1,Employees::getCompanyId, companyId));
        return Result.success(BeanUtil.copyToList(list, EmployeesResultVO.class));
    }

    @PostMapping("/add")
    @Operation(summary = "新增员工")
    public Result<?> add(@Valid @RequestBody EmployeesSaveVO employeesSaveVO) {
        return Result.success(employeesService.add(employeesSaveVO));
    }
    @PutMapping("/update")
    @Operation(summary = "更新员工")
    public Result<?> updateEmployees(@Valid @RequestBody EmployeesUpdateVO Employees) {
        Employees employees1 = BeanUtil.copyProperties(Employees, Employees.class);

        return Result.success(employeesService.updateEmployees(employees1));
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "删除员工")
    public Result<?> deleteEmployees(@PathVariable String id) {
        return Result.success(employeesService.removeEmployeeById(id));
    }
    //todo excel导入接口

}
