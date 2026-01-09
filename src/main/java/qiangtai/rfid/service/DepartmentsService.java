package qiangtai.rfid.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import qiangtai.rfid.dto.req.DepartmentQuery;
import qiangtai.rfid.dto.req.DepartmentsResultVO;
import qiangtai.rfid.dto.req.DepartmentsSaveVO;
import qiangtai.rfid.entity.Departments;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author FEI
* @description 针对表【departments(部门信息表)】的数据库操作Service
* @createDate 2025-12-25 09:00:07
*/
public interface DepartmentsService extends IService<Departments> {

    Page<DepartmentsResultVO> pageDepartments(DepartmentQuery departmentQuery);

    Boolean add(DepartmentsSaveVO departmentsSaveVO);

    Boolean removeDepartById(Integer id);

    Boolean updateDepartments(Departments departments1);

    List<DepartmentsResultVO> listDepartments(DepartmentQuery departmentQuery);
}
