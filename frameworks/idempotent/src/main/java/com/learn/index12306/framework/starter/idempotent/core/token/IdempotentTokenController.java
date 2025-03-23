package com.learn.index12306.framework.starter.idempotent.core.token;

import com.learn.index12306.framework.starter.convention.result.Result;
import com.learn.index12306.framework.starter.web.Results;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: cyy
 * @create: 2025-03-22 17:17
 * @description: 基于 Token 验证请求幂等性控制器
 **/
@RestController
@RequiredArgsConstructor
public class IdempotentTokenController {

    private final IdempotentTokenService idempotentTokenService;

    /**
     * 请求申请Token
     */
    @GetMapping("/token")
    public Result<String> createToken() {
        return Results.success(idempotentTokenService.createToken());
    }
}
