package qiangtai.rfid.dto;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginVO {

    /**
     * 用户名
     */
    @NotEmpty
    private String username;

    /**
     * 密码
     */
    @NotEmpty
    private String password;

}
