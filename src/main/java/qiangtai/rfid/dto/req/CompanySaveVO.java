package qiangtai.rfid.dto.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CompanySaveVO {

    /**
     * 公司名称
     */
    @NotEmpty(message = "公司名称不能为空")
    private String companyName;

    /**
     * 办公地址
     */
    @NotEmpty(message = "办公地址不能为空")
    private String address;

    /**
     * 联系电话
     */
    @NotEmpty(message = "联系电话不能为空")
    private String phone;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
