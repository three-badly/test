package qiangtai.rfid.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserUpdatePasswordVO {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull(message = "用户id不能为空")
    private Integer id;

    @NotEmpty(message = "旧密码不能为空")
    private String oldPassword;

    @NotEmpty(message = "新密码不能为空")
    private String newPassword;
}
