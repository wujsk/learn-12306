package com.learn.index12306.framework.starter.bizs.userservice.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.learn.index12306.framework.starter.bizs.userservice.common.constants.RedisKeyConstant;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request.UserUpdateReqDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.response.UserQueryActualRespDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.response.UserQueryRespDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.entity.UserDO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.entity.UserDeletionDO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.entity.UserMailDO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.mapper.UserDeletionMapper;
import com.learn.index12306.framework.starter.bizs.userservice.dao.mapper.UserMailMapper;
import com.learn.index12306.framework.starter.bizs.userservice.dao.mapper.UserMapper;
import com.learn.index12306.framework.starter.bizs.userservice.service.UserService;
import com.learn.index12306.framework.starter.cache.DistributedCache;
import com.learn.index12306.framework.starter.common.util.BeanUtil;
import com.learn.index12306.framework.starter.convention.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.learn.index12306.framework.starter.bizs.userservice.util.VaildateUtil.isValidEmail;

/**
 * @author: cyy
 * @create: 2025-03-23 18:17
 * @description: 用户信息实现类
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDeletionMapper userDeletionMapper;
    private final DistributedCache distributedCache;
    private final RedissonClient redissonClient;
    private final UserMapper userMapper;
    private final UserMailMapper userMailMapper;

    @Override
    public UserQueryRespDTO queryUserByUserId(String userId) {
        UserDO userDO = userMapper.selectOne(Wrappers.<UserDO>lambdaQuery()
                .eq(UserDO::getId, userId));
        if (userDO == null) {
            throw new ServiceException("用户不存在");
        }
        return BeanUtil.convert(userDO, UserQueryRespDTO.class);
    }

    @Override
    public UserQueryRespDTO queryUserByUsername(String username) {
        UserDO userDO = userMapper.selectOne(Wrappers.<UserDO>lambdaQuery()
                .eq(UserDO::getUsername, username));
        if (userDO == null) {
            throw new ServiceException("用户不存在");
        }
        return BeanUtil.convert(userDO, UserQueryRespDTO.class);
    }

    @Override
    public UserQueryActualRespDTO queryActualUserByUsername(String username) {
        return BeanUtil.convert(queryUserByUsername(username), UserQueryActualRespDTO.class);
    }

    @Override
    public Integer queryUserDeletionNum(Integer idType, String idCard) {
        Long deletionCount = distributedCache.get(RedisKeyConstant.USER_DELETION_COUNT + ":" + idType + ":" + idCard, Long.class);
        if (deletionCount == null) {
            String key = RedisKeyConstant.USER_DELETION_LOCK + ":" + idType + ":" + idCard;
            RLock lock = redissonClient.getLock(key);
            lock.lock();
            try {
                deletionCount = distributedCache.get(key, Long.class);
                if (deletionCount == null) {
                    deletionCount = userDeletionMapper.selectCount(Wrappers.<UserDeletionDO>lambdaQuery()
                            .eq(UserDeletionDO::getIdType, idType)
                            .eq(UserDeletionDO::getIdCard, idCard));
                    deletionCount = Optional.ofNullable(deletionCount).orElse(0L);
                    distributedCache.put(RedisKeyConstant.USER_DELETION_COUNT + ":" + idType + ":" + idCard, deletionCount, 10, TimeUnit.MINUTES);
                }
            } finally {
                lock.unlock();
            }
        }
        return deletionCount.intValue();
    }

    @Override
    public void update(UserUpdateReqDTO requestParam) {
        UserQueryRespDTO userQueryRespDTO = queryUserByUsername(requestParam.getUsername());
        UserDO userDO = BeanUtil.convert(requestParam, UserDO.class);
        userMapper.update(userDO, Wrappers.lambdaUpdate(UserDO.class)
                .eq(UserDO::getUsername, requestParam.getUsername()));
        String mail = requestParam.getMail();
        if (StrUtil.isNotBlank(mail) && !Objects.equals(userQueryRespDTO.getMail(), mail) && isValidEmail(mail)) {
            userMailMapper.delete(Wrappers.lambdaUpdate(UserMailDO.class)
                    .eq(UserMailDO::getMail, userQueryRespDTO.getMail()));
            UserMailDO userMailDO = UserMailDO.builder()
                    .mail(mail)
                    .username(requestParam.getUsername())
                    .build();
            userMailMapper.insert(userMailDO);
        }
    }
}
