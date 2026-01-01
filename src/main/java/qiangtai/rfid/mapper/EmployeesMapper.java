package qiangtai.rfid.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import qiangtai.rfid.entity.Employees;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author FEI
* @description 针对表【employees(员工信息表)】的数据库操作Mapper
* @createDate 2025-12-25 18:15:02
* @Entity qiangtai.rfid.entity.Employees
*/
@Mapper
public interface EmployeesMapper extends MPJBaseMapper<Employees> {

}




