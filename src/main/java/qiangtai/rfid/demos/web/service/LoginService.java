package qiangtai.rfid.demos.web.service;

import qiangtai.rfid.demos.web.dto.LoginVO;
import qiangtai.rfid.demos.web.dto.UserResultVO;

public interface LoginService {

    UserResultVO login(LoginVO loginVO);

    UserResultVO register(LoginVO user);
}
