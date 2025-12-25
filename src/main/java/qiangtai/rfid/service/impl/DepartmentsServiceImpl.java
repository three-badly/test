package qiangtai.rfid.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import qiangtai.rfid.context.UserContext;
import qiangtai.rfid.dto.req.DepartmentQuery;
import qiangtai.rfid.dto.req.DepartmentsSaveVO;
import qiangtai.rfid.entity.Departments;
import qiangtai.rfid.handler.exception.BusinessException;
import qiangtai.rfid.service.DepartmentsService;
import qiangtai.rfid.mapper.DepartmentsMapper;
import org.springframework.stereotype.Service;

/**
* @author FEI
* @description 针对表【departments(部门信息表)】的数据库操作Service实现
* @createDate 2025-12-25 09:00:07
*/
@Service
public class DepartmentsServiceImpl extends ServiceImpl<DepartmentsMapper, Departments>
    implements DepartmentsService{

    @Override
    public Page<Departments> pageDepartments(DepartmentQuery departmentQuery) {
        Page<Departments> page = new Page<>(departmentQuery.getCurrent(), departmentQuery.getSize());
        Integer companyId = UserContext.get().getCompanyId();
        if (companyId == null){
            throw new BusinessException(1020, "公司不存在");
        }
        return this.page(page, Wrappers.<Departments>lambdaQuery().eq(Departments::getCompanyId, companyId));
    }

    @Override
    public Boolean add(DepartmentsSaveVO departmentsSaveVO) {
        Integer companyId = UserContext.get().getCompanyId();
        if (companyId == null){
            throw new BusinessException(1020, "公司不存在");
        }
        //校验部门名称是否重复
        if (this.count(Wrappers.<Departments>lambdaQuery().eq(Departments::getDepartmentName, departmentsSaveVO.getDepartmentName())) > 0){
            throw new BusinessException(1021, "部门名称重复");
        }
        //存入当前线程公司id
        departmentsSaveVO.setCompanyId(companyId);

        return this.save(BeanUtil.copyProperties(departmentsSaveVO, Departments.class));
    }

    @Override
    public Boolean removeDepartById(Integer id) {
        Integer companyId = UserContext.get().getCompanyId();
        if (companyId == null){
            throw new BusinessException(1020, "公司不存在");
        }
        //校验部门是否存在
        if (this.count(Wrappers.<Departments>lambdaQuery().eq(Departments::getId, id)) == 0){
            throw new BusinessException(1022, "部门不存在");
        }
        return this.remove(Wrappers.<Departments>lambdaQuery().eq(Departments::getId, id).eq(Departments::getCompanyId, companyId));
    }
}




