package com.learn.index12306.framework.starter.bizs.userservice.controller;

import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request.UserDeletionReqDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request.UserLoginReqDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request.UserRegisterReqDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.response.UserLoginRespDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.response.UserRegisterRespDTO;
import com.learn.index12306.framework.starter.bizs.userservice.service.UserLoginService;
import com.learn.index12306.framework.starter.convention.result.Result;
import com.learn.index12306.framework.starter.web.Results;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: cyy
 * @create: 2025-03-23 12:31
 **/
@RestController
@RequiredArgsConstructor
@Tag(name = "用户登录注册注销相关接口", description = "用户登录注册注销相关接口")
public class UserLoginController {

    private final UserLoginService userLoginService ;

    @PostMapping("/user/login")
    @Schema(description = "用户登录")
    public Result<UserLoginRespDTO> login(@RequestBody UserLoginReqDTO requestParam){
        return Results.success(userLoginService.login(requestParam));
    }

    /**
     * 通过 Token 检查用户是否登录
     */
    @GetMapping("/user/check-login")
    @Schema(description = "通过 Token 检查用户是否登录")
    public Result<UserLoginRespDTO> checkLogin(@RequestParam("accessToken") String accessToken) {
        UserLoginRespDTO result = userLoginService.checkLogin(accessToken);
        return Results.success(result);
    }

    /**
     * 用户退出登录
     */
    @GetMapping("/user/logout")
    @Schema(description = "用户退出登录")
    public Result<Void> logout(@RequestParam(required = false) String accessToken) {
        userLoginService.logout(accessToken);
        return Results.success();
    }

    /**
     * 用户注册
     */
    @PostMapping("/user/register")
    @Schema(description = "用户注册")
    public Result<UserRegisterRespDTO> register(@RequestBody UserRegisterReqDTO registerReqDTO) {
        return Results.success(userLoginService.register(registerReqDTO));
    }

    /**
     * 检查用户名是否已存在
     */
    @GetMapping("/user/has-username")
    @Schema(description = "检查用户名是否已存在")
    public Result<Boolean> hasUsername(@RequestParam("username") @NotEmpty String username) {
        return Results.success(userLoginService.hasUsername(username));
    }

    /**
     * 注销用户
     */
    @PostMapping("/user/deletion")
    @Schema(description = "注销用户")
    public Result<Void> deletion(@RequestBody @Validated UserDeletionReqDTO requestParam) {
        userLoginService.deletion(requestParam);
        return Results.success();
    }
}
