package qiangtai.rfid.demos.web.dto;

import lombok.Data;

@Data
public class UserResultVO {

    /**
     * 身份[0- 1-]
     */
    private String identity;

    /**
     * 用户名
     */
    private String username;


    /**
     * 公司id
     */
    private String companyId;

    private String token;




}
