package qiangtai.rfid.dto.rsp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
