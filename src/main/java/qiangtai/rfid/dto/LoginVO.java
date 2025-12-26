package qiangtai.rfid.dto;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginVO {

    /**
     * 登录账号
     */
    @NotEmpty
    private String account;

    /**
     * 密码
     */
    @NotEmpty
    private String password;

}
