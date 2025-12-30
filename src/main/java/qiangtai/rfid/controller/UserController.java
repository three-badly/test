package qiangtai.rfid.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import qiangtai.rfid.dto.LoginVO;

import qiangtai.rfid.dto.req.UserMobileNameUpadteVO;
import qiangtai.rfid.dto.req.UserQuery;
import qiangtai.rfid.dto.req.UserSaveVO;
import qiangtai.rfid.dto.req.UserUpdatePasswordVO;
import qiangtai.rfid.dto.result.Result;
import qiangtai.rfid.service.UserService;

import javax.validation.Valid;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
@Validated
@Tag(name = "用户接口")
public class UserController {


    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "登录")
    public Result<?> login(@Valid @RequestBody LoginVO user) {
        return Result.success(userService.login(user));
    }

    @PostMapping("/addUser")
    @Operation(summary = "添加公司用户")
    public Result<?> addUser(@Valid @RequestBody UserSaveVO userSaveVO) {
        return Result.success(userService.addUser(userSaveVO));

    }

    @GetMapping("/pageUser")
    @Operation(summary = "分页展示所有用户")
    public Result<?> pageUser(UserQuery userQuery) {
        return Result.success(userService.pageUser(userQuery));

    }

    @GetMapping("/listUser")
    @Operation(summary = "列表展示所有用户")
    public Result<?> listUser() {
        return Result.success(userService.listUser());
    }
    @GetMapping("/{id}")
    @Operation(summary = "通过id获取用户信息")
    public Result<?> detail(@PathVariable Integer id) {
        return Result.success(userService.detail(id));
    }

    @PutMapping("/updatePassword")
    @Operation(summary = "修改密码")
    public Result<?> updatePassword(@Valid @RequestBody UserUpdatePasswordVO userUpdatePasswordVO) {
        return Result.success(userService.updatePassword(userUpdatePasswordVO));
    }
    @PutMapping("/updateMobileName")
    @Operation(summary = "修改手机号，账号名，账号拥有者姓名")
    public Result<?> updateMobileName(@Valid @RequestBody UserMobileNameUpadteVO userMobileNameUpadteVO) {
        return Result.success(userService.updateMobileName(userMobileNameUpadteVO));
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户")
    public Result<?> deleteUser(@PathVariable("id") Integer id) {
        return Result.success(userService.deleteUser(id));
    }
}
