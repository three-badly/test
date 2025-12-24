package qiangtai.rfid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import qiangtai.rfid.entity.User;

@Mapper
public interface LoginMapper extends BaseMapper<User> {
}
