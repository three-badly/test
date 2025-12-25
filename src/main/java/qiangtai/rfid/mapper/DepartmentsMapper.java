package qiangtai.rfid.mapper;

import org.apache.ibatis.annotations.Mapper;
import qiangtai.rfid.entity.Departments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author FEI
* @description 针对表【departments(部门信息表)】的数据库操作Mapper
* @createDate 2025-12-25 09:00:07
* @Entity qiangtai.rfid.entity.Departments
*/
@Mapper
public interface DepartmentsMapper extends BaseMapper<Departments> {

}




