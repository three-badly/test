package qiangtai.rfid.demos.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import qiangtai.rfid.demos.web.dto.LoginVO;
import qiangtai.rfid.demos.web.dto.UserResultVO;
import qiangtai.rfid.demos.web.entity.User;
import qiangtai.rfid.demos.web.service.LoginService;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
@Validated
@Tag(name = "登录接口")
public class LoginController {


    private final LoginService loginService;

    @GetMapping("/login")
    @Operation(summary = "登录")
    public UserResultVO login(@Validated LoginVO user) {
        loginService.login(user);
        return null;
    }
    @GetMapping("/register")
    @Operation(summary = "注册")
    public UserResultVO register(@Validated LoginVO user) {

        return loginService.register(user);
    }
}
