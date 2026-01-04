package qiangtai.rfid.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;


import qiangtai.rfid.dto.req.CompanyQuery;
import qiangtai.rfid.dto.req.CompanySaveVO;
import qiangtai.rfid.dto.rsp.CompanyNameId;
import qiangtai.rfid.dto.result.Result;
import qiangtai.rfid.dto.rsp.CompanyResultVO;
import qiangtai.rfid.entity.Company;
import qiangtai.rfid.service.CompanyService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author FEI
 */
@RequestMapping("/company")
@RestController
@RequiredArgsConstructor
@Tag(name = "公司管理接口")
public class CompanyController {
    
    private final CompanyService companyService;

    @GetMapping("/getCompanyList")
    @Operation(summary = "获取公司列表")
    public Result<List<CompanyNameId>> getCompanyList() {
        List<Company> companies = companyService.getCompanyList();
        return Result.success(BeanUtil.copyToList(companies, CompanyNameId.class));
    }
    @GetMapping("/pageCompany")
    @Operation(summary = "分页展示公司")
    public Result<Page<CompanyResultVO>> pageCompany(@Valid @ParameterObject CompanyQuery companyQuery) {
        return Result.success(companyService.pageCompany(companyQuery));
    }



    @PostMapping("/add")
    @Operation(summary = "新增公司")
    public Result<Boolean> createCompany(@Valid @RequestBody CompanySaveVO company) {
        return Result.success(companyService.createCompany(company));
    }

    @PutMapping("/update")
    @Operation(summary = "更新公司信息")
    public Result<Boolean> updateCompany(@RequestBody Company company) {
        return Result.success(companyService.updateCompany(company));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除公司")
    public Result<Boolean> deleteCompany(@PathVariable Integer id) {

        return Result.success(companyService.deleteCompany(id));
    }
}
