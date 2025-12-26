package qiangtai.rfid.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import qiangtai.rfid.dto.LoginVO;

import qiangtai.rfid.dto.req.UserQuery;
import qiangtai.rfid.dto.req.UserSaveVO;
import qiangtai.rfid.dto.result.Result;
import qiangtai.rfid.entity.User;
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
    public Result<?> login(@Valid @RequestBody LoginVO user) {
        return Result.success(loginService.login(user));
    }

    @PostMapping("/addUser")
    @Operation(summary = "添加公司用户")
    public Result<?> addUser(@Valid @RequestBody UserSaveVO userSaveVO) {
        return Result.success(loginService.addUser(userSaveVO));

    }

    @GetMapping("/pageUser")
    @Operation(summary = "分页展示所有用户")
    public Result<?> pageUser(UserQuery userQuery) {
        return Result.success(loginService.pageUser(userQuery));

    }

    @GetMapping("/listUser")
    @Operation(summary = "列表展示所有用户")
    public Result<?> listUser() {
        return Result.success(loginService.listUser());

    }
}
