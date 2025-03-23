package com.learn.index12306.framework.starter.idempotent.annotation;

import cn.hutool.core.annotation.AliasFor;
import com.learn.index12306.framework.starter.idempotent.enums.IdempotentSceneEnum;
import com.learn.index12306.framework.starter.idempotent.enums.IdempotentTypeEnum;

import java.lang.annotation.*;

/**
* @author: cyy
* @create: 2025-03-22 15:22
 * @description: MQ 业务场景幂等注解
**/
@Deprecated
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Idempotent(scene = IdempotentSceneEnum.MQ)
public @interface MQIdempotent {

    /**
     * {@link Idempotent#key} 的别名
     */
    @AliasFor(annotation = Idempotent.class, attribute = "key")
    String key() default "";

    /**
     * {@link Idempotent#type} 的别名
     */
    @AliasFor(annotation = Idempotent.class, attribute = "type")
    IdempotentTypeEnum type() default IdempotentTypeEnum.SPEL;
}
