package qiangtai.rfid.dto.rsp;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompanyResultVO {

    /**
     * 公司名称
     */

    private String companyName;

    /**
     * 办公地址
     */

    private String address;

    /**
     * 联系电话
     */

    private String phone;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
