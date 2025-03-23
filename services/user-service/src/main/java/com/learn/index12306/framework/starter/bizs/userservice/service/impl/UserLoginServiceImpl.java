package com.learn.index12306.framework.starter.bizs.userservice.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request.UserDeletionReqDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request.UserLoginReqDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request.UserRegisterReqDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.response.UserLoginRespDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.response.UserRegisterRespDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.entity.UserDO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.mapper.UserMapper;
import com.learn.index12306.framework.starter.bizs.userservice.service.UserLoginService;
import com.learn.index12306.framework.starter.convention.exception.ClientException;
import com.learn.index12306.framework.starter.convention.exception.ServiceException;
import com.learn.index12306.framework.starter.cache.DistributedCache;
import com.learn.index12306.framework.starter.designpattern.chain.AbstractChainContext;
import com.learn.index12306.framework.starter.user.core.UserInfoDTO;
import com.learn.index12306.framework.starter.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBloomFilter;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.learn.index12306.framework.starter.bizs.userservice.common.constants.RedisKeyConstant.USER_REGISTER_REUSE_SHARDING;
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
    public UserRegisterRespDTO register(UserRegisterReqDTO requestParam) {
        return null;
    }

    @Override
    public void deletion(UserDeletionReqDTO requestParam) {

    }
}
