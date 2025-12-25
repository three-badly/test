package qiangtai.rfid.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import qiangtai.rfid.dto.req.CompanyQuery;
import qiangtai.rfid.dto.req.CompanySaveVO;
import qiangtai.rfid.dto.rsp.CompanyResultVO;
import qiangtai.rfid.entity.Company;

import javax.validation.Valid;
import java.util.List;

public interface CompanyService {
    
    /**
     * 获取公司列表
     * @return 公司列表
     */
    List<Company> getCompanyList();

    /**
     * 创建公司
     *
     * @param company 公司信息
     * @return 创建结果
     */
    Boolean createCompany(CompanySaveVO company);
    
    /**
     * 更新公司信息
     * @param company 公司信息
     * @return 更新结果
     */
    Boolean updateCompany(Company company);
    
    /**
     * 删除公司
     * @param id 公司ID
     * @return 删除结果
     */
    Boolean deleteCompany(Integer id);

    Page<CompanyResultVO> pageCompany(@Valid CompanyQuery companyQuery);
}
