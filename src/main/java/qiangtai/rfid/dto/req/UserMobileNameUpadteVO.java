package qiangtai.rfid.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserMobileNameUpadteVO {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull(message = "用户id不能为空")
    private Integer id;

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
}
