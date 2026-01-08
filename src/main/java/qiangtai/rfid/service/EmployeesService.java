package qiangtai.rfid.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import qiangtai.rfid.dto.req.EmployeesQuery;
import qiangtai.rfid.dto.req.EmployeesSaveVO;
import qiangtai.rfid.dto.result.Result;
import qiangtai.rfid.dto.rsp.EmployeesResultVO;
import qiangtai.rfid.entity.Employees;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author FEI
* @description 针对表【employees(员工信息表)】的数据库操作Service
* @createDate 2025-12-25 18:15:02
*/
public interface EmployeesService extends IService<Employees> {

    Boolean add(EmployeesSaveVO employeesSaveVO);

    Boolean removeEmployeeById(String id);

    Page<EmployeesResultVO> pageEmployees(EmployeesQuery employeesQuery);

    Boolean updateEmployees(Employees employees1);

    Result<?> importExcel(List<Employees> successList);

    List<EmployeesResultVO> listEmployees(EmployeesQuery employeesQuery);
}
