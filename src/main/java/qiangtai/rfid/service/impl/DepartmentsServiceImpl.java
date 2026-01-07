package qiangtai.rfid.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
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

import java.util.List;
import java.util.stream.Collectors;

/**
* @author FEI
* @description 针对表【departments(部门信息表)】的数据库操作Service实现
* @createDate 2025-12-25 09:00:07
*/
@Service
public class DepartmentsServiceImpl extends ServiceImpl<DepartmentsMapper, Departments>
    implements DepartmentsService{

    private final DepartmentsMapper departmentsMapper;

    public DepartmentsServiceImpl(DepartmentsMapper departmentsMapper) {
        this.departmentsMapper = departmentsMapper;
    }

    @Override
    public Page<Departments> pageDepartments(DepartmentQuery departmentQuery) {
        Page<Departments> page = new Page<>(departmentQuery.getCurrent(), departmentQuery.getSize());
        Integer companyId = UserContext.get().getCompanyId();
        if (companyId == null){
            throw new BusinessException(1020, "公司不存在");
        }
        if (companyId == -1){
            return this.page(page, Wrappers.<Departments>emptyWrapper());
        }
        return this.page(page, Wrappers.<Departments>lambdaQuery().eq(Departments::getCompanyId, companyId));
    }

    @Override
    public Boolean add(DepartmentsSaveVO departmentsSaveVO) {
        Integer companyId = UserContext.get().getCompanyId();
        if (companyId == null || companyId == -1){
            throw new BusinessException(1020, "公司不存在或不要用管理员账号新建部门");
        }
        //校验部门名称是否重复
        if (this.count(Wrappers.<Departments>lambdaQuery()
                        .eq(Departments::getCompanyId, companyId)
                .eq(Departments::getDepartmentName, departmentsSaveVO.getDepartmentName())) > 0){
            throw new BusinessException(1021, "部门名称重复");
        }
        //存入当前线程公司id
        departmentsSaveVO.setCompanyId(companyId);
        departmentsSaveVO.setDeptCode(getDeptCode(companyId));
        return this.save(BeanUtil.copyProperties(departmentsSaveVO, Departments.class));
    }

    public String getDeptCode(Integer companyId){
        String s = "D-" + RandomUtil.randomNumbers(3);
        List<String> codes = departmentsMapper.selectList(Wrappers.<Departments>lambdaQuery()
                .eq(companyId != null, Departments::getCompanyId, companyId)).stream().map(Departments::getDeptCode).collect(Collectors.toList());
        while (codes.contains(s)){
            s = "D" + RandomUtil.randomNumbers(3);
        }
        return s;
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

    @Override
    public Boolean updateDepartments(Departments departments1) {

        //限定只可修改自己的公司的部门
        Integer companyId = UserContext.get().getCompanyId();
        if (companyId == -1){
            throw new BusinessException(1020, "请不要用一级管理员修改部门");
        }
        //校验部门是否存在
        if (this.count(Wrappers.<Departments>lambdaQuery()
                .eq(Departments::getId, departments1.getId())
                .eq(Departments::getCompanyId, companyId)
        ) == 0){
            throw new BusinessException(1022, "当前公司部门不存在");
        }
        return this.updateById(departments1);
    }
}




