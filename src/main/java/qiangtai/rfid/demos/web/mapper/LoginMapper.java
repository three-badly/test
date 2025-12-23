package qiangtai.rfid.demos.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import qiangtai.rfid.demos.web.entity.User;

@Mapper
public interface LoginMapper extends BaseMapper<User> {
}
