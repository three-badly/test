package qiangtai.rfid.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import qiangtai.rfid.dto.LoginVO;
import qiangtai.rfid.dto.req.*;
import qiangtai.rfid.dto.rsp.UserNameInfo;
import qiangtai.rfid.dto.rsp.UserResultVO;
import qiangtai.rfid.entity.User;

import java.util.List;


/**
 * @author FEI
 *
 *
 */
public interface UserService extends IService<User> {

    UserResultVO login(LoginVO loginVO);

    UserNameInfo addUser(UserSaveVO userSaveVO);

    Page<UserResultVO> pageUser(UserQuery userQuery);

    List<UserResultVO> listUser();

    Boolean updatePassword(UserUpdatePasswordVO userUpdatePasswordVO);

    Boolean updateMobileName(UserMobileNameUpadteVO userMobileNameUpadteVO);

    UserResultVO detail(Integer id);

    Boolean deleteUser(Integer id);

    Boolean resetPassword(ResetPasswordVO resetPasswordVO);
}
