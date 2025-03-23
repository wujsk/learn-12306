package com.learn.index12306.framework.starter.bizs.userservice.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.learn.index12306.framework.starter.bizs.userservice.common.enums.UserChainMarkEnum;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request.UserDeletionReqDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request.UserLoginReqDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request.UserRegisterReqDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.response.UserLoginRespDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.response.UserQueryRespDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.response.UserRegisterRespDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.entity.*;
import com.learn.index12306.framework.starter.bizs.userservice.dao.mapper.*;
import com.learn.index12306.framework.starter.bizs.userservice.service.UserLoginService;
import com.learn.index12306.framework.starter.bizs.userservice.service.UserService;
import com.learn.index12306.framework.starter.common.util.BeanUtil;
import com.learn.index12306.framework.starter.convention.exception.ClientException;
import com.learn.index12306.framework.starter.convention.exception.ServiceException;
import com.learn.index12306.framework.starter.cache.DistributedCache;
import com.learn.index12306.framework.starter.designpattern.chain.AbstractChainContext;
import com.learn.index12306.framework.starter.user.core.UserContext;
import com.learn.index12306.framework.starter.user.core.UserInfoDTO;
import com.learn.index12306.framework.starter.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.learn.index12306.framework.starter.bizs.userservice.common.constants.RedisKeyConstant.*;
import static com.learn.index12306.framework.starter.bizs.userservice.common.enums.UserRegisterErrorCodeEnum.*;
import static com.learn.index12306.framework.starter.bizs.userservice.util.UserReuseUtil.hashShardingIdx;

/**
 * @author: cyy
 * @create: 2025-03-23 12:53
 * @description: 用户登录接口实现
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {

    private final UserMapper userMapper;
    private final DistributedCache distributedCache;
    private final AbstractChainContext<UserRegisterReqDTO> abstractChainContext;
    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;
    private final RedissonClient redissonClient;
    private final UserPhoneMapper userPhoneMapper;
    private final UserMailMapper userMailMapper;
    private final UserReuseMapper userReuseMapper;
    private final UserService userService;
    private final UserDeletionMapper userDeletionMapper;

    @Override
    public UserLoginRespDTO login(UserLoginReqDTO requestParam) {
        String usernameOrMailOrPhone = requestParam.getUsernameOrMailOrPhone();
        if (StringUtils.isBlank(usernameOrMailOrPhone)) {
            throw new ClientException("用户名/手机号/邮箱不能为空");
        }
        boolean mailFlag = false;
        for (char c : usernameOrMailOrPhone.toCharArray()) {
            if (c == '@') {
                mailFlag = true;
                break;
            }
        }
        String username = usernameOrMailOrPhone;
        if (mailFlag) {
            UserDO userDO = userMapper.selectOne(Wrappers.<UserDO>lambdaQuery()
                    .eq(UserDO::getMail, usernameOrMailOrPhone));
            username = Optional.ofNullable(userDO).map(UserDO::getUsername)
                    .orElseThrow(() -> new ClientException("用户名/手机号/邮箱不存在"));
        } else {
            UserDO userDO = userMapper.selectOne(Wrappers.<UserDO>lambdaQuery()
                    .eq(UserDO::getPhone, usernameOrMailOrPhone));
            username = Optional.ofNullable(userDO).map(UserDO::getUsername)
                    .orElseThrow(() -> new ClientException("用户名/手机号/邮箱不存在"));
        }
        UserDO userDO = userMapper.selectOne(Wrappers.<UserDO>lambdaQuery()
                .eq(UserDO::getUsername, username)
                .eq(UserDO::getPassword, requestParam.getPassword())
                .select(UserDO::getId, UserDO::getUsername, UserDO::getRealName));
        if (userDO != null) {
            UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                    .userId(userDO.getId())
                    .username(userDO.getUsername())
                    .realname(userDO.getRealName())
                    .build();
            String accessToken = JwtUtil.generateAccessToken(userInfoDTO);

            UserLoginRespDTO userLoginRespDTO = new UserLoginRespDTO(String.valueOf(userDO.getId()),
                    userDO.getUsername(),
                    userDO.getRealName(),
                    accessToken);
            distributedCache.put(accessToken, userLoginRespDTO, 30, TimeUnit.MINUTES);
            return userLoginRespDTO;
        }
        throw new ServiceException("账号不存在或密码错误");
    }

    @Override
    public UserLoginRespDTO checkLogin(String accessToken) {
        return distributedCache.get(accessToken, UserLoginRespDTO.class);
    }

    @Override
    public void logout(String accessToken) {
        if (StringUtils.isNotBlank(accessToken)) {
            distributedCache.delete(accessToken);
        }
    }

    @Override
    public Boolean hasUsername(String username) {
        boolean hasUsername = userRegisterCachePenetrationBloomFilter.contains(username);
        if (hasUsername) {
            StringRedisTemplate instance = (StringRedisTemplate) distributedCache.getInstance();
            return instance.opsForSet().isMember(USER_REGISTER_REUSE_SHARDING + hashShardingIdx(username), username);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserRegisterRespDTO register(UserRegisterReqDTO requestParam) {
        abstractChainContext.handle(UserChainMarkEnum.USER_REGISTER_FILTER.name(), requestParam);
        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER + requestParam.getUsername());
        boolean tryLock = lock.tryLock();
        if (!tryLock) {
            throw new ServiceException(HAS_USERNAME_NOTNULL);
        }
        try {
            try {
                int inserted = userMapper.insert(BeanUtil.convert(requestParam, UserDO.class));
                if (inserted < 1) {
                    throw new ServiceException(USER_REGISTER_FAIL);
                }
            } catch (DuplicateKeyException e) {
                log.error("用户名 [{}] 重复注册", requestParam.getUsername());
                throw new ServiceException(HAS_USERNAME_NOTNULL);
            }
            if (StrUtil.isNotBlank(requestParam.getPhone())) {
                UserPhoneDO userPhoneDO = UserPhoneDO.builder()
                        .phone(requestParam.getPhone())
                        .username(requestParam.getUsername())
                        .build();
                try {
                    userPhoneMapper.insert(userPhoneDO);
                } catch (DuplicateKeyException dke) {
                    log.error("用户 [{}] 注册手机号 [{}] 重复", requestParam.getUsername(), requestParam.getPhone());
                    throw new ServiceException(PHONE_REGISTERED);
                }
            }
            if (StrUtil.isNotBlank(requestParam.getMail())) {
                UserMailDO userMailDO = UserMailDO.builder()
                        .mail(requestParam.getMail())
                        .username(requestParam.getUsername())
                        .build();
                try {
                    userMailMapper.insert(userMailDO);
                } catch (DuplicateKeyException dke) {
                    log.error("用户 [{}] 注册邮箱 [{}] 重复", requestParam.getUsername(), requestParam.getMail());
                    throw new ServiceException(MAIL_REGISTERED);
                }
            }
            String username = requestParam.getUsername();
            userReuseMapper.delete(Wrappers.<UserReuseDO>lambdaQuery()
                    .eq(UserReuseDO::getUsername, username));
            StringRedisTemplate instance = (StringRedisTemplate) distributedCache.getInstance();
            instance.opsForSet().remove(USER_REGISTER_REUSE_SHARDING + hashShardingIdx(username), username);
            userRegisterCachePenetrationBloomFilter.add(username);
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public void deletion(UserDeletionReqDTO requestParam) {
        String username = UserContext.getUsername();
        if (!Objects.equals(username, requestParam.getUsername())) {
            throw new ClientException("注销账号与登录账号不一致");
        }
        RLock lock = redissonClient.getLock(USER_DELETION + requestParam.getUsername());
        lock.lock();
        try {
            UserQueryRespDTO userQueryRespDTO = userService.queryUserByUsername(username);
            UserDeletionDO userDeletionDO = UserDeletionDO.builder()
                    .idType(userQueryRespDTO.getIdType())
                    .idCard(userQueryRespDTO.getIdCard())
                    .build();
            userDeletionMapper.insert(userDeletionDO);
            UserDO userDO = new UserDO();
            userDO.setDeletionTime(System.currentTimeMillis());
            userDO.setUsername(username);
            userMapper.deletionUser(userDO);
            if (StrUtil.isNotBlank(userQueryRespDTO.getPhone())) {
                UserPhoneDO userPhoneDO = UserPhoneDO.builder()
                        .phone(userQueryRespDTO.getPhone())
                        .deletionTime(System.currentTimeMillis())
                        .build();
                userPhoneMapper.deletionUser(userPhoneDO);
            }
            if (StrUtil.isNotBlank(userQueryRespDTO.getMail())) {
                UserMailDO userMailDO = UserMailDO.builder()
                        .mail(userQueryRespDTO.getMail())
                        .deletionTime(System.currentTimeMillis())
                        .build();
                userMailMapper.deletionUser(userMailDO);
            }
            distributedCache.delete(UserContext.getToken());
            userReuseMapper.insert(new UserReuseDO(username));
            StringRedisTemplate instance = (StringRedisTemplate) distributedCache.getInstance();
            instance.opsForSet().add(USER_REGISTER_REUSE_SHARDING + hashShardingIdx(username), username);
        } finally {
            lock.unlock();
        }
    }
}
