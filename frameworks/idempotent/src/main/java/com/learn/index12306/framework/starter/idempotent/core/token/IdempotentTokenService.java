package com.learn.index12306.framework.starter.idempotent.core.token;

import com.learn.index12306.framework.starter.idempotent.core.IdempotentExecuteHandler;

/**
 * @author: cyy
 * @create: 2025-03-22 17:12
 * @description: Token 实现幂等接口
 **/
public interface IdempotentTokenService extends IdempotentExecuteHandler {

    /**
     * 创建幂等验证Token
     */
    String createToken();
}
