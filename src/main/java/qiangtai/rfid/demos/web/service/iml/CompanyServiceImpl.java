package qiangtai.rfid.demos.web.service.iml;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import qiangtai.rfid.demos.web.dto.UserResultVO;
import qiangtai.rfid.demos.web.entity.Company;
import qiangtai.rfid.demos.web.mapper.CompanyMapper;
import qiangtai.rfid.demos.web.service.CompanyService;

import java.time.LocalDateTime;
import java.util.List;

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
    public UserResultVO createCompany(Company company) {
//        log.info("创建公司: {}", company);
//        company.setCreatedAt(LocalDateTime.now());
//        int result = companyMapper.insert(company);
//        if (result > 0) {
//            return UserResultVO.success("公司创建成功");
//        }
//        return UserResultVO.error("公司创建失败");
        return null;
    }
    
    @Override
    public UserResultVO updateCompany(Company company) {
        /*log.info("更新公司信息: {}", company);
        int result = companyMapper.updateById(company);
        if (result > 0) {
            return UserResultVO.success("公司信息更新成功");
        }
        return UserResultVO.error("公司信息更新失败");*/
        return null;
    }
    
    @Override
    public UserResultVO deleteCompany(Integer id) {
        /*log.info("删除公司: {}", id);
        int result = companyMapper.deleteById(id);
        if (result > 0) {
            return UserResultVO.success("公司删除成功");
        }
        return UserResultVO.error("公司删除失败");*/
        return null;
    }
}
