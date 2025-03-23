package com.learn.index12306.framework.starter.bizs.userservice.controller;

import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request.UserUpdateReqDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.response.UserQueryActualRespDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.response.UserQueryRespDTO;
import com.learn.index12306.framework.starter.bizs.userservice.service.UserService;
import com.learn.index12306.framework.starter.convention.result.Result;
import com.learn.index12306.framework.starter.web.Results;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: cyy
 * @create: 2025-03-23 20:32
 **/
@RestController
@RequiredArgsConstructor
@Tag(name = "用户信息相关接口", description = "用户信息相关接口")
public class UserInfoController {

    private final UserService userService;

    /**
     * 根据用户名查询用户信息
     */
    @Schema(description = "根据用户名查询用户信息")
    @GetMapping("/user/query")
    public Result<UserQueryRespDTO> queryUserByUsername(@RequestParam("username") @NotEmpty String username) {
        return Results.success(userService.queryUserByUsername(username));
    }

    /**
     * 根据用户名查询用户无脱敏信息
     */
    @Schema(description = "根据用户名查询用户无脱敏信息")
    @GetMapping("/user/actual/query")
    public Result<UserQueryActualRespDTO> queryActualUserByUsername(@RequestParam("username") @NotEmpty String username) {
        return Results.success(userService.queryActualUserByUsername(username));
    }

    /**
     * 修改用户
     */
    @Schema(description = "修改用户")
    @PostMapping("/user/update")
    public Result<Void> update(@RequestBody @Validated UserUpdateReqDTO requestParam) {
        userService.update(requestParam);
        return Results.success();
    }
}
