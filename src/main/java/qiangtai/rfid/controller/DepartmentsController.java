package qiangtai.rfid.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import qiangtai.rfid.context.UserContext;
import qiangtai.rfid.dto.req.DepartmentQuery;
import qiangtai.rfid.dto.req.DepartmentsSaveVO;
import qiangtai.rfid.dto.req.DepartmentsUpdateVO;
import qiangtai.rfid.dto.result.Result;
import qiangtai.rfid.entity.Departments;
import qiangtai.rfid.service.DepartmentsService;

import javax.validation.Valid;

@RequestMapping("/departments")
@RestController
@RequiredArgsConstructor
@Validated
@Tag(name = "部门接口")
public class DepartmentsController {
    private final DepartmentsService departmentsService;

    @GetMapping("/pageDepartments")
    @Operation(summary = "部门多,分页查看部门")
    public Result<?> pageDepartments(@ParameterObject DepartmentQuery departmentQuery) {
        return Result.success(departmentsService.pageDepartments(departmentQuery));
    }
    @GetMapping("/listDepartments")
    @Operation(summary = "部门少,列表查看部门")
    public Result<?> listDepartments() {
        Integer companyId = UserContext.get().getCompanyId();
        //平台看全部
        if (companyId == -1){
            return Result.success(departmentsService.list());
        }
        return Result.success(departmentsService.list(Wrappers.<Departments>lambdaQuery().eq(Departments::getCompanyId, companyId)));
    }

    @PostMapping("/add")
    @Operation(summary = "新增部门")
    public Result<?> add(@Valid @RequestBody DepartmentsSaveVO departmentsSaveVO) {
        return Result.success(departmentsService.add(departmentsSaveVO));
    }
    @PutMapping("/update")
    @Operation(summary = "更新部门")
    public Result<?> updateDepartments(@Valid @RequestBody DepartmentsUpdateVO departments) {
        Departments departments1 = BeanUtil.copyProperties(departments, Departments.class);

        return Result.success(departmentsService.updateDepartments(departments1));
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "删除部门")
    public Result<?> deleteDepartments(@PathVariable Integer id) {
        return Result.success(departmentsService.removeDepartById(id));
    }
}
