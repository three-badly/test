package qiangtai.rfid.demos.web.dto;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginVO {

    /**
     * 公司id -1为管理员
     */
    private String companyId = "-1";
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
