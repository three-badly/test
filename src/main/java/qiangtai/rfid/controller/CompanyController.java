package qiangtai.rfid.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import qiangtai.rfid.dto.req.CompanyQuery;
import qiangtai.rfid.dto.req.CompanySaveVO;
import qiangtai.rfid.dto.result.CompanyNameId;
import qiangtai.rfid.dto.result.Result;
import qiangtai.rfid.dto.rsp.CompanyResultVO;
import qiangtai.rfid.entity.Company;
import qiangtai.rfid.service.CompanyService;

import javax.validation.Valid;
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
    @GetMapping("/pageCompany")
    @Operation(summary = "分页展示公司")
    public Result<?> pageCompany(@Valid CompanyQuery companyQuery) {
        Page<CompanyResultVO> companies = companyService.pageCompany(companyQuery);
        return Result.success(companies);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取公司信息")
    public Result<?> getCompanyById(@PathVariable Integer id) {
        Company company = companyService.getCompanyById(id);
        return Result.success(company);
    }

    @PostMapping("/add")
    @Operation(summary = "新增公司")
    public Result<?> createCompany(@Valid @RequestBody CompanySaveVO company) {

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
