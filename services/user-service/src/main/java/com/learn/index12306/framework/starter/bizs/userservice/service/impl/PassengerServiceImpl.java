package com.learn.index12306.framework.starter.bizs.userservice.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.learn.index12306.framework.starter.bizs.userservice.common.enums.VerifyStatusEnum;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request.PassengerRemoveReqDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request.PassengerReqDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.response.PassengerActualRespDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.response.PassengerRespDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.entity.PassengerDO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.mapper.PassengerMapper;
import com.learn.index12306.framework.starter.bizs.userservice.service.PassengerService;
import com.learn.index12306.framework.starter.bizs.userservice.util.VaildateUtil;
import com.learn.index12306.framework.starter.cache.DistributedCache;
import com.learn.index12306.framework.starter.common.util.BeanUtil;
import com.learn.index12306.framework.starter.convention.exception.ClientException;
import com.learn.index12306.framework.starter.convention.exception.ServiceException;
import com.learn.index12306.framework.starter.user.core.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.learn.index12306.framework.starter.bizs.userservice.common.constants.RedisKeyConstant.USER_PASSENGER_LIST;

/**
 * @author: cyy
 * @create: 2025-03-23 21:04
 * @description: 乘车人service
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final DistributedCache distributedCache;
    private final PassengerMapper passengerMapper;

    @Override
    public List<PassengerRespDTO> listPassengerQueryByUsername(String username) {
        String actualUserPassengerListStr = getActualUserPassengerListStr(username);
        return Optional.ofNullable(actualUserPassengerListStr)
                .map(each -> JSON.parseArray(each, PassengerDO.class))
                .map(each -> BeanUtil.convert(each, PassengerRespDTO.class))
                .orElse(null);
    }

    @Override
    public List<PassengerActualRespDTO> listPassengerQueryByIds(String username, List<Long> ids) {
        String actualUserPassengerListStr = getActualUserPassengerListStr(username);
        if (StrUtil.isBlank(actualUserPassengerListStr)) {
            return null;
        }
        return JSON.parseArray(actualUserPassengerListStr, PassengerDO.class).stream()
                .filter(each -> ids.contains(each.getId()))
                .map(each -> BeanUtil.convert(each, PassengerActualRespDTO.class))
                .collect(Collectors.toList());
    }

    private String getActualUserPassengerListStr(String username) {
        return distributedCache.safeGet(USER_PASSENGER_LIST + username,
                String.class,
                () -> {
                    List<PassengerDO> passengerDOList = passengerMapper.selectList(Wrappers.lambdaQuery(PassengerDO.class)
                            .eq(PassengerDO::getUsername, username));
                    return CollUtil.isNotEmpty(passengerDOList) ? JSON.toJSONString(passengerDOList) : null;
                },
                1,
                TimeUnit.DAYS);
    }

    @Override
    public void savePassenger(PassengerReqDTO requestParam) {
        verifyPassenger(requestParam);
        String username = UserContext.getUsername();
        try {
            PassengerDO passengerDO = BeanUtil.convert(requestParam, PassengerDO.class);
            passengerDO.setUsername(username);
            passengerDO.setCreateDate(LocalDateTime.now());
            passengerDO.setVerifyStatus(VerifyStatusEnum.UNREVIEWED.getCode());
            int insert = passengerMapper.insert(passengerDO);
            if (!SqlHelper.retBool(insert)){
                throw new ServiceException(String.format("[%s] 新增乘车人失败", username));
            }
        } catch (Exception e) {
            if (e instanceof ServiceException) {
                log.error("{}，请求参数：{}", e.getMessage(), JSON.toJSONString(requestParam));
            } else {
                log.error("[{}] 新增乘车人失败，请求参数：{}", username, JSON.toJSONString(requestParam), e);
            }
            throw e;
        }
        delUserPassengerCache(username);
    }

    @Override
    public void updatePassenger(PassengerReqDTO requestParam) {
        verifyPassenger(requestParam);
        String username = UserContext.getUsername();
        try {
            PassengerDO passengerDO = BeanUtil.convert(requestParam, PassengerDO.class);
            passengerDO.setUsername(username);
            int update = passengerMapper.update(passengerDO, Wrappers.<PassengerDO>lambdaUpdate()
                    .eq(PassengerDO::getId, requestParam.getId())
                    .eq(PassengerDO::getUsername, username));
            if (!SqlHelper.retBool(update)){
                throw new ServiceException(String.format("[%s] 更新乘车人失败", username));
            }
        } catch (Exception e) {
            if (e instanceof ServiceException) {
                log.error("{}，请求参数：{}", e.getMessage(), JSON.toJSONString(requestParam));
            } else {
                log.error("[{}] 更新乘车人失败，请求参数：{}", username, JSON.toJSONString(requestParam), e);
            }
            throw e;
        }
        delUserPassengerCache(username);
    }

    @Override
    public void removePassenger(PassengerRemoveReqDTO requestParam) {
        String username = UserContext.getUsername();
        PassengerDO passengerDO = selectPassenger(username, requestParam.getId());
        if (Objects.isNull(passengerDO)) {
            throw new ClientException("乘车人数据不存在");
        }
        try {
            // 逻辑删除，修改数据库表记录 del_flag
            int deleted = passengerMapper.delete(Wrappers.lambdaUpdate(PassengerDO.class)
                    .eq(PassengerDO::getUsername, username)
                    .eq(PassengerDO::getId, requestParam.getId()));
            if (!SqlHelper.retBool(deleted)) {
                throw new ServiceException(String.format("[%s] 删除乘车人失败", username));
            }
        } catch (Exception ex) {
            if (ex instanceof ServiceException) {
                log.error("{}，请求参数：{}", ex.getMessage(), JSON.toJSONString(requestParam));
            } else {
                log.error("[{}] 删除乘车人失败，请求参数：{}", username, JSON.toJSONString(requestParam), ex);
            }
            throw ex;
        }
        delUserPassengerCache(username);
    }

    private PassengerDO selectPassenger(String username, Long passengerId) {
        return passengerMapper.selectOne(Wrappers.lambdaQuery(PassengerDO.class)
                .eq(PassengerDO::getUsername, username)
                .eq(PassengerDO::getId, passengerId));
    }

    private void delUserPassengerCache(String username) {
        distributedCache.delete(USER_PASSENGER_LIST + username);
    }

    private void verifyPassenger(PassengerReqDTO requestParam) {
        int length = requestParam.getRealName().length();
        if (!(length >= 2 && length <= 16)) {
            throw new ClientException("乘车人名称请设置2-16位的长度");
        }
        if (!VaildateUtil.isValidIDCard(requestParam.getIdCard())) {
            throw new ClientException("乘车人证件号错误");
        }
        if (!VaildateUtil.isValidPhoneNumber(requestParam.getPhone())) {
            throw new ClientException("乘车人手机号错误");
        }
    }
}
