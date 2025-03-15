package com.learn.index12306.frameworks.starter.designpattern.strategy;

import com.learn.index12306.framework.starter.bases.ApplicationContextHolder;
import com.learn.index12306.framework.starter.bases.init.ApplicationInitializingEvent;
import com.learn.index12306.framework.starter.convention.exception.ServiceException;
import org.springframework.context.ApplicationListener;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author: cyy
 * @create: 2025-03-15 14:07
 * @description: 策略选择器
 **/
public class AbstractStrategyChoose implements ApplicationListener<ApplicationInitializingEvent> {

    /**
     * 执行策略集合
     */
    private final Map<String, AbstractExecuteStrategy> abstractExecuteStrategyMap = new HashMap<>();

    /**
     * 根据 mark 查询具体策略
     * @param mark          策略标识
     * @param predicateFlag 匹配范解析标识
     * @return 实际执行策略
     */
    public AbstractExecuteStrategy choose(String mark, Boolean predicateFlag) {
        if (predicateFlag != null && predicateFlag) {
            abstractExecuteStrategyMap.values().stream()
                    .filter(each -> StringUtils.hasText(each.patternMathMark()))
                    .filter(each -> Pattern.compile(each.patternMathMark()).matcher(mark).matches())
                    .findFirst()
                    .orElseThrow(() -> new ServiceException(String.format("[%s] 策略未定义", mark)));
        }
        return Optional.ofNullable(abstractExecuteStrategyMap.get(mark))
                .orElseThrow(() -> new ServiceException(String.format("[%s] 策略未定义", mark)));
    }

    /**
     * 根据 mark 查询具体策略并执行
     * @param mark         策略标识
     * @param requestParam 执行策略入参
     * @param <REQUEST>    执行策略入参范型
     */
    public <REQUEST> void chooseAndExecute(String mark, REQUEST requestParam) {
        AbstractExecuteStrategy executeStrategy  = choose(mark, null);
        executeStrategy .execute(requestParam);
    }

    /**
     * 根据 mark 查询具体策略并执行
     * @param mark          策略标识
     * @param requestParam  执行策略入参
     * @param predicateFlag 匹配范解析标识
     * @param <REQUEST>     执行策略入参范型
     */
    public <REQUEST> void chooseAndExecute(String mark, REQUEST requestParam, Boolean predicateFlag) {
        AbstractExecuteStrategy executeStrategy = choose(mark, predicateFlag);
        executeStrategy.execute(requestParam);
    }

    /**
     * 根据 mark 查询具体策略并执行，带返回值
     * @param mark         策略标识
     * @param requestParam 执行策略入参
     * @param <REQUEST>    执行策略入参范型
     * @param <RESPONSE>   执行策略返回值范型
     * @return 执行策略返回值
     */
    public <REQUEST, RESPONSE> RESPONSE chooseAndExecuteResp(String mark, REQUEST requestParam) {
        AbstractExecuteStrategy executeStrategy  = choose(mark, null);
        return (RESPONSE) executeStrategy.executeResp(requestParam);
    }

    @Override
    public void onApplicationEvent(ApplicationInitializingEvent event) {
        Map<String, AbstractExecuteStrategy> actual =
                ApplicationContextHolder.getBeansOfType(AbstractExecuteStrategy.class);
        actual.forEach((beanName, bean) -> {
            AbstractExecuteStrategy abstractExecuteStrategy = abstractExecuteStrategyMap.get(bean.mark());
            if (abstractExecuteStrategy != null) {
                throw new ServiceException(String.format("[%s] Duplicate execution policy", bean.mark()));
            }
            abstractExecuteStrategyMap.put(bean.mark(), bean);
        });
    }
}
