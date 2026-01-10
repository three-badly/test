package qiangtai.rfid.config;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.annotation.Configuration;
import qiangtai.rfid.constant.Constant;
import qiangtai.rfid.entity.User;
import qiangtai.rfid.mapper.UserMapper;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RootInitConfig implements SmartInitializingSingleton {

    private final UserMapper userMapper;   // 你的 MyBatis-Plus Mapper

    @Override
    public void afterSingletonsInstantiated() {
        // 只跑一次，重启才再跑
        long count = userMapper.selectCount(
                Wrappers.<User>lambdaQuery().eq(User::getAccount, Constant.ROOT_NAME));
        if (count == 0) {
            User root = new User();
            root.setAccount(Constant.ROOT_NAME);
            root.setUsername(Constant.ROOT_NAME);
            root.setCompanyId(-1);
            //密码盐值
            String salt = RandomUtil.randomString(20);
            String defPassword = SecureUtil.sha256(Constant.ROOT_PASSWORD + salt);
            root.setSalt(salt);
            root.setPassword(defPassword);
            root.setRole(Constant.ROOT_ROLE);
            userMapper.insert(root);
            log.warn(">>> {} 账号不存在，已自动创建（密码 {}）",Constant.ROOT_NAME,Constant.ROOT_PASSWORD);
        } else {
            log.info(">>> {} 账号已存在，跳过初始化",Constant.ROOT_NAME);
        }
    }
}