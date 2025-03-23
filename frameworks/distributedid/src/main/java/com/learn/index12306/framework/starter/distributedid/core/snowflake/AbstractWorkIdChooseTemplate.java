package com.learn.index12306.framework.starter.distributedid.core.snowflake;

import cn.hutool.core.date.SystemClock;
import com.learn.index12306.framework.starter.distributedid.util.SnowflakeIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author: cyy
 * @create: 2025-03-19 15:06
 * @description: 雪花算法模板生成
 **/
@Slf4j
public abstract class AbstractWorkIdChooseTemplate {

    /**
     * 是否使用 {@link SystemClock} 获取当前时间戳
     */
    @Value("${framework.distributed.id.snowflake.is-use-system-clock:false}")
    private boolean isUseSystemClock;

    protected abstract WorkIdWrapper chooseWorkId();

    public void chooseAndInit() {
        WorkIdWrapper workIdWrapper = chooseWorkId();
        Long workId = workIdWrapper.getWorkId();
        Long dataCenterId = workIdWrapper.getDataCenterId();
        Snowflake snowflake = new Snowflake(workId, dataCenterId, isUseSystemClock);
        log.info("Snowflake type: {}, workId: {}, dataCenterId: {}", this.getClass().getSimpleName(), workId, dataCenterId);
        SnowflakeIdUtil.initSnowFlake(snowflake);
    }
}
