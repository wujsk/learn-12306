package com.learn.index12306.starter.idempotent.annotation;

import cn.hutool.core.annotation.AliasFor;
import com.learn.index12306.starter.idempotent.enums.IdempotentTypeEnum;

/**
 * @author: cyy
 * @create: 2025-03-22 15:26
 * @description: RestAPI业务场景幂等注解
 **/
public @interface RestAPIIdempotent {

    /**
     * {@link Idempotent#key} 的别名
     */
    @AliasFor(annotation = Idempotent.class, attribute = "key")
    String key() default "";

    /**
     * {@link Idempotent#message} 的别名
     */
    @AliasFor(annotation = Idempotent.class, attribute = "message")
    String message() default "您操作太快，请稍后再试";

    /**
     * {@link Idempotent#type} 的别名
     */
    @AliasFor(annotation = Idempotent.class, attribute = "type")
    IdempotentTypeEnum type() default IdempotentTypeEnum.PARAM;
}
