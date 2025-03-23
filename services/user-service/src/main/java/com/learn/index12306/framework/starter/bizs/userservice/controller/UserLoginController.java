package com.learn.index12306.framework.starter.bizs.userservice.controller;

import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request.UserLoginReqDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.response.UserLoginRespDTO;
import com.learn.index12306.framework.starter.bizs.userservice.service.UserLoginService;
import com.learn.index12306.framework.starter.convention.result.Result;
import com.learn.index12306.framework.starter.web.Results;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: cyy
 * @create: 2025-03-23 12:31
 **/
@RestController("/user/")
@RequiredArgsConstructor
@Tag(name = "用户登录注册相关接口", description = "用户登录注册相关接口")
public class UserLoginController {

    private final UserLoginService userLoginService ;

    @PostMapping("login")
    public Result<UserLoginRespDTO> login(@RequestBody UserLoginReqDTO requestParam){
        return Results.success(userLoginService.login(requestParam));
    }
}
