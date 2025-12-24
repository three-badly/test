package qiangtai.rfid.controller;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import qiangtai.rfid.dto.result.CompanyNameId;
import qiangtai.rfid.dto.result.Result;
import qiangtai.rfid.entity.Company;
import qiangtai.rfid.service.CompanyService;

import java.util.List;

@RequestMapping("/company")
@RestController
@RequiredArgsConstructor
@Tag(name = "公司接口")
public class CompanyController {
    
    private final CompanyService companyService;

    @GetMapping("/getCompanyList")
    @Operation(summary = "获取公司列表")
    public Result<?> getCompanyList() {
        List<Company> companies = companyService.getCompanyList();
        return Result.success(BeanUtil.copyToList(companies, CompanyNameId.class));
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取公司信息")
    public Result<?> getCompanyById(@PathVariable Integer id) {
        Company company = companyService.getCompanyById(id);
        return Result.success(company);
    }

    @PostMapping("/create")
    @Operation(summary = "创建公司")
    public Result<?> createCompany(@RequestBody Company company) {

        return Result.success(companyService.createCompany(company));
    }

    @PutMapping("/update")
    @Operation(summary = "更新公司信息")
    public Result<?> updateCompany(@RequestBody Company company) {

        return Result.success(companyService.updateCompany(company));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除公司")
    public Result<?> deleteCompany(@PathVariable Integer id) {

        return Result.success(companyService.deleteCompany(id));
    }
}
