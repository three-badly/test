package qiangtai.rfid.service;

import qiangtai.rfid.dto.LoginVO;
import qiangtai.rfid.dto.req.UserSaveVO;
import qiangtai.rfid.dto.rsp.UserNameInfo;
import qiangtai.rfid.dto.rsp.UserResultVO;


public interface LoginService {

    UserResultVO login(LoginVO loginVO);



    UserNameInfo addUser(UserSaveVO userSaveVO);
}
