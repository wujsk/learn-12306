package com.learn.index12306.framework.starter.bizs.filter;

import com.learn.index12306.framework.starter.bases.constant.UserConstant;
import com.learn.index12306.framework.starter.bizs.config.Config;
import com.learn.index12306.framework.starter.bizs.util.JwtUtil;
import com.learn.index12306.framework.starter.bizs.util.UserInfoDTO;
import com.learn.index12306.framework.starter.cache.DistributedCache;
import jakarta.annotation.Resource;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * @author: cyy
 * @create: 2025-03-24 08:34
 * @description: SpringCloud Gateway Token 拦截器
 **/
@Component
public class TokenValidateGatewayFilterFactory extends AbstractGatewayFilterFactory<Config> {

    @Resource
    private DistributedCache distributedCache;

    public TokenValidateGatewayFilterFactory() {
        super(Config.class);
    }

    /**
     * 注销用户时需要传递 Token
     */
    public static final String DELETION_PATH = "/user/deletion";

    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                ServerHttpRequest request = exchange.getRequest();
                String requestPath  = request.getPath().toString();
                if (isPathInBlackPreList(requestPath, config.getBlackPathPre())) {
                    String accessToken = request.getHeaders().getFirst("Authorization");
                    String tokenCache = distributedCache.get(accessToken, String.class);
                    if (!StringUtils.hasText(tokenCache)) {
                        return unauthorizedResponse(exchange);
                    } 
                    UserInfoDTO userInfo = JwtUtil.parseJwtToken(accessToken);
                    if (userInfo == null) {
                        return unauthorizedResponse(exchange);
                    }
                    ServerHttpRequest.Builder builder = exchange.getRequest().mutate().headers(httpHeaders -> {
                        httpHeaders.set(UserConstant.USER_ID_KEY, String.valueOf(userInfo.getUserId()));
                        httpHeaders.set(UserConstant.USER_NAME_KEY, userInfo.getUsername());
                        httpHeaders.set(UserConstant.REAL_NAME_KEY, URLEncoder.encode(userInfo.getRealName(), StandardCharsets.UTF_8));
                        if (Objects.equals(requestPath, DELETION_PATH)) {
                            httpHeaders.set(UserConstant.USER_TOKEN_KEY, accessToken);
                        }
                    });
                    return chain.filter(exchange.mutate().request(builder.build()).build());
                }
                return chain.filter(exchange);
            }
        };
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private boolean isPathInBlackPreList(String requestPath, List<String> blackPathPre) {
        if (CollectionUtils.isEmpty(blackPathPre)) {
            return false;
        }
        return blackPathPre.stream().anyMatch(requestPath::startsWith);
    }
}
