package qiangtai.rfid.demos.web.service;

import qiangtai.rfid.demos.web.dto.UserResultVO;
import qiangtai.rfid.demos.web.entity.Company;

import java.util.List;

public interface CompanyService {
    
    /**
     * 获取公司列表
     * @return 公司列表
     */
    List<Company> getCompanyList();
    
    /**
     * 根据ID获取公司信息
     * @param id 公司ID
     * @return 公司信息
     */
    Company getCompanyById(Integer id);
    
    /**
     * 创建公司
     * @param company 公司信息
     * @return 创建结果
     */
    UserResultVO createCompany(Company company);
    
    /**
     * 更新公司信息
     * @param company 公司信息
     * @return 更新结果
     */
    UserResultVO updateCompany(Company company);
    
    /**
     * 删除公司
     * @param id 公司ID
     * @return 删除结果
     */
    UserResultVO deleteCompany(Integer id);
}
