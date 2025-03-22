package com.learn.index12306.starter.idempotent.core;

import com.learn.index12306.framework.starter.bases.ApplicationContextHolder;
import com.learn.index12306.starter.idempotent.core.param.IdempotentParamService;
import com.learn.index12306.starter.idempotent.core.spel.IdempotentSpELByMQExecuteHandler;
import com.learn.index12306.starter.idempotent.enums.IdempotentSceneEnum;
import com.learn.index12306.starter.idempotent.enums.IdempotentTypeEnum;

/**
 * @author: cyy
 * @create: 2025-03-22 17:08
 * @description: 幂等执行处理器工厂
 **/
public final class IdempotentExecuteHandlerFactory {

    /**
     * 获取幂等执行处理器
     *
     * @param scene 指定幂等验证场景类型
     * @param type  指定幂等处理类型
     * @return 幂等执行处理器
     */
    public static IdempotentExecuteHandler getInstance(IdempotentSceneEnum scene, IdempotentTypeEnum type) {
        IdempotentExecuteHandler result = null;
        switch (scene) {
            case RESTAPI -> {
                switch (type) {
                    case PARAM -> result = ApplicationContextHolder.getBean(IdempotentParamService.class);
                    case TOKEN -> result = ApplicationContextHolder.getBean(IdempotentTokenService.class);
                    case SPEL -> result = ApplicationContextHolder.getBean(IdempotentSpELByRestAPIExecuteHandler.class);
                    default -> {
                    }
                }
            }
            case MQ -> result = ApplicationContextHolder.getBean(IdempotentSpELByMQExecuteHandler.class);
            default -> {
            }
        }
        return result;
    }
}
