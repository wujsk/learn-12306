package com.learn.index12306.frameworks.starter.distributedid.core.snowflake;

import cn.hutool.core.collection.CollUtil;
import com.learn.index12306.frameworks.starter.bases.ApplicationContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: cyy
 * @create: 2025-03-21 18:35
 * @description: 使用 Redis 获取雪花 WorkId
 **/
@Slf4j
public class LocalRedisWorkIdChoose extends AbstractWorkIdChooseTemplate implements InitializingBean {

    private StringRedisTemplate stringRedisTemplate;

    public LocalRedisWorkIdChoose() {
        stringRedisTemplate = ApplicationContextHolder.getBean(StringRedisTemplate.class);
    }

    @Override
    protected WorkIdWrapper chooseWorkId() {
        DefaultRedisScript redisScript = new DefaultRedisScript<>();;
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/chooseWorkId.lua")));
        List<Long> luaResultList = null;
        try {
            redisScript.setResultType(List.class);
            luaResultList = (ArrayList) this.stringRedisTemplate.execute(redisScript, null);
        } catch (Exception ex) {
            log.error("Redis Lua 脚本获取 WorkId 失败", ex);
        }
        return CollUtil.isEmpty(luaResultList) ? new RandomWorkIdChoose().chooseWorkId() : new WorkIdWrapper(luaResultList.get(0), luaResultList.get(1));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        chooseAndInit();
    }
}
