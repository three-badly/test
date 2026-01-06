package qiangtai.rfid.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import qiangtai.rfid.dto.req.LoginVO;

import qiangtai.rfid.dto.req.*;
import qiangtai.rfid.dto.result.Result;
import qiangtai.rfid.dto.rsp.UserResultVO;
import qiangtai.rfid.service.UserService;

import javax.validation.Valid;
import java.util.List;


/**
 * @author FEI
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
    public Result<UserResultVO> login(@Valid @RequestBody LoginVO user) {
        return Result.success(userService.login(user), "登陆成功！");
    }

    @PostMapping("/addUser")
    @Operation(summary = "添加公司用户")
    public Result<Boolean> addUser(@Valid @RequestBody UserSaveVO userSaveVO) {
        return Result.success(userService.addUser(userSaveVO), "添加成功");

    }

    @GetMapping("/pageUser")
    @Operation(summary = "分页展示所有用户")
    public Result<Page<UserResultVO>> pageUser(@ParameterObject UserQuery userQuery) {
        return Result.success(userService.pageUser(userQuery), "查询成功");
    }

    @GetMapping("/listUser")
    @Operation(summary = "列表展示所有用户")
    public Result<List<UserResultVO>> listUser(@ParameterObject UserQuery userQuery) {
        return Result.success(userService.listUser(userQuery), "查询成功");
    }

    @GetMapping("/{id}")
    @Operation(summary = "通过id获取用户信息")
    public Result<UserResultVO> detail(@PathVariable Integer id) {
        return Result.success(userService.detail(id), "查询成功");
    }

    @PutMapping("/updatePassword")
    @Operation(summary = "修改密码")
    public Result<Boolean> updatePassword(@Valid @RequestBody UserUpdatePasswordVO userUpdatePasswordVO) {
        return Result.success(userService.updatePassword(userUpdatePasswordVO), "修改成功");
    }

    @PutMapping("/updateMobileName")
    @Operation(summary = "修改手机号，账号名，账号拥有者姓名，公司名字")
    public Result<Boolean> updateMobileName(@Valid @RequestBody UserMobileNameUpdateVO userMobileNameUpdateVO) {
        return Result.success(userService.updateMobileName(userMobileNameUpdateVO), "修改成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户")
    public Result<Boolean> deleteUser(@PathVariable Integer id) {
        return Result.success(userService.deleteUser(id), "删除成功");
    }

    @PutMapping("/resetPassword")
    @Operation(summary = "重置密码")
    public Result<Boolean> resetPassword(@Validated @RequestBody ResetPasswordVO resetPasswordVO) {
        return Result.success(userService.resetPassword(resetPasswordVO), "重置密码成功");
    }

}
