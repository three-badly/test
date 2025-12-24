package qiangtai.rfid.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import qiangtai.rfid.dto.LoginVO;

import qiangtai.rfid.dto.rsp.UserNameInfo;
import qiangtai.rfid.dto.rsp.UserResultVO;
import qiangtai.rfid.service.LoginService;

import javax.validation.Valid;

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

    @PostMapping("/login")
    @Operation(summary = "登录")
    public UserResultVO login(@Valid @RequestBody LoginVO user) {
        return loginService.login(user);
    }
    @GetMapping("/register")
    @Operation(summary = "注册")
    public UserResultVO register(@Validated LoginVO user) {

        return loginService.register(user);
    }
    @PostMapping("/addUser")
    @Operation(summary = "添加公司用户")
    public UserNameInfo addUser(String companyId) {
        return loginService.addUser(companyId);

    }
}
