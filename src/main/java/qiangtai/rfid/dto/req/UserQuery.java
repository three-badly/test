package qiangtai.rfid.dto.req;

import lombok.Data;

@Data
public class UserQuery extends PageRequst{
    /**
     * 账号持有人名字模糊查询
     */
    private String username;
    /**
     * 公司名称模糊查询
     */
    private String companyName;

}
