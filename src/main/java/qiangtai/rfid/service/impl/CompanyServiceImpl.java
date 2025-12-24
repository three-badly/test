package qiangtai.rfid.service.impl;

import cn.hutool.core.bean.BeanException;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import qiangtai.rfid.dto.req.CompanyQuery;
import qiangtai.rfid.dto.req.CompanySaveVO;
import qiangtai.rfid.dto.rsp.CompanyResultVO;
import qiangtai.rfid.dto.rsp.UserResultVO;
import qiangtai.rfid.entity.Company;
import qiangtai.rfid.handler.exception.BusinessException;
import qiangtai.rfid.mapper.CompanyMapper;
import qiangtai.rfid.service.CompanyService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyServiceImpl  implements CompanyService {
    
    private final CompanyMapper companyMapper;
    
    @Override
    public List<Company> getCompanyList() {
        log.info("获取公司列表");
        return companyMapper.selectList(Wrappers.emptyWrapper());
    }
    
    @Override
    public Company getCompanyById(Integer id) {
        log.info("根据ID获取公司信息: {}", id);
        return companyMapper.selectById(id);
    }
    
    @Override
    public Boolean createCompany(CompanySaveVO companySaveVO) {
        //校验参数
        List<Company> companies = companyMapper.selectList(Wrappers.lambdaQuery()
        );
        List<String> companyNames = companies.stream().map(Company::getCompanyName).collect(Collectors.toList());
        boolean contains = companyNames.contains(companySaveVO.getCompanyName());
        if (contains) {
            throw new BusinessException(10010,"公司名称已存在");
        }
        int result = companyMapper.insert(BeanUtil.copyProperties(companySaveVO, Company.class));
        return result > 0;
    }
    
    @Override
    public Boolean updateCompany(Company company) {
        //校验参数

        int result = companyMapper.updateById(company);
        return result > 0;
    }
    
    @Override
    public Boolean deleteCompany(Integer id) {

        int result = companyMapper.deleteById(id);
        return result > 0;
    }

    @Override
    public Page<CompanyResultVO> pageCompany(CompanyQuery companyQuery) {
        //模糊查询
        Page<Company> companyPage = companyMapper.selectPage(new Page<>(companyQuery.getCurrent(), companyQuery.getSize()), Wrappers.<Company>lambdaQuery()
                        .like(StringUtils.isNotBlank(companyQuery.getCompanyName()),
                                Company::getCompanyName, companyQuery.getCompanyName()));
        List<CompanyResultVO> collect = companyPage.getRecords().stream().map(company -> BeanUtil.copyProperties(company, CompanyResultVO.class)).collect(Collectors.toList());
        Page<CompanyResultVO> companyResultVOPage = new Page<>();
        BeanUtil.copyProperties(companyPage, companyResultVOPage);
        companyResultVOPage.setRecords(collect);
        return companyResultVOPage;
    }
}
