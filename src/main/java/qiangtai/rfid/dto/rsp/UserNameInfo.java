package qiangtai.rfid.dto.rsp;

import lombok.Data;

@Data
public class UserNameInfo {
    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 用户名
     */
    private String username;


    /**
     * 密码
     */
    private String password;
    private Integer companyId;

}
