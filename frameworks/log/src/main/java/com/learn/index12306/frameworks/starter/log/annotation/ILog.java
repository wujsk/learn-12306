package com.learn.index12306.frameworks.starter.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: cyy
 * @create: 2025-03-15 15:32
 * @description: Log 注解打印，可以标记在类或者方法上
 **/
@Target({ElementType.TYPE, ElementType.METHOD}) // 指定注解作用在类上或者方法上
@Retention(RetentionPolicy.RUNTIME) // 指定注解在运行时有效
public @interface  ILog {

    /**
     * 入参打印
     * @return 打印结果中是否包含入参，{@link Boolean#TRUE} 打印，{@link Boolean#FALSE} 不打印
     */
    boolean input() default true;

    /**
     * 出参打印
     * @return 打印结果中是否包含出参，{@link Boolean#TRUE} 打印，{@link Boolean#FALSE} 不打印
     */
    boolean output() default true;
}
