package qiangtai.rfid.dto.rsp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class UserResultVO {
    /**
     * ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 身份[0- 1-]
     */
    private String role;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 登录账号
     */
    private String account;
    /**
     * 账号持有人名字
     */
    private String username;
    /**
     * 公司id
     */
    private Integer companyId;
    private String token;
    private String companyName;












}
