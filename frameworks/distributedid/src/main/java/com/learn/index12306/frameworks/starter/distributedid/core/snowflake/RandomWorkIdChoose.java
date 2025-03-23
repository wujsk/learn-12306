package com.learn.index12306.frameworks.starter.distributedid.core.snowflake;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author: cyy
 * @create: 2025-03-19 15:16
 * @description: 使用随机数获取雪花 WorkId
 **/
public class RandomWorkIdChoose extends AbstractWorkIdChooseTemplate implements InitializingBean {

    @Override
    protected WorkIdWrapper chooseWorkId() {
        int min = 0, max = 31;
        return new WorkIdWrapper(getRandom(min, max), getRandom(min, max));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        chooseAndInit();
    }

    private static long getRandom(long min, long max) {
        return (long) (Math.random() * (max - min + 1) + min);
    }
}
