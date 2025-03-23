package com.learn.index12306.framework.starter.user.util;

import com.alibaba.fastjson2.JSON;
import com.learn.index12306.framework.starter.bases.constant.UserConstant;
import com.learn.index12306.framework.starter.user.core.UserInfoDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: cyy
 * @create: 2025-03-12 08:41
 **/
@Slf4j
public class JwtUtil {

    private static final long EXPIRE_TIME = 60 * 60 * 24;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String ISS = "index12306";
    public static final String SECRET = "SecretKey039245678901232039487623456783092349288901402967890140939827";

    /**
     * 生成JWT Token
     * @param userInfoDTO 用户信息
     * @return token
     */
    public static String generateAccessToken(UserInfoDTO userInfoDTO) {
        Map<String, Object> customerUserMap  = new HashMap<>();
        customerUserMap .put(UserConstant.USER_ID_KEY, userInfoDTO.getUserId());
        customerUserMap .put(UserConstant.USER_NAME_KEY, userInfoDTO.getUsername());
        customerUserMap .put(UserConstant.REAL_NAME_KEY, userInfoDTO.getRealname());
        String token = Jwts.builder()
                //设置签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, SECRET)
                //设置签发者
                .setIssuer(ISS)
                //设置签发时间
                .setIssuedAt(new Date())
                //设置主题（通常是用户信息）
                .setSubject(JSON.toJSONString(customerUserMap))
                //设置过期时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME * 1000))
                .compact();  //生成最终的 JWT 字符串
        return TOKEN_PREFIX + token;
    }

    /**
     * 解析JWT Token
     * @param token 用户访问 Token
     * @return 用户信息
     */
    public static UserInfoDTO parseJwtToken(String token) {
        if (StringUtils.hasText(token)) {
            String actualToken = token.replace(TOKEN_PREFIX, "");
            try {
                Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(actualToken).getBody();
                Date expiration = claims.getExpiration();
                if (expiration.after(new Date())) {
                    return JSON.parseObject(claims.getSubject(), UserInfoDTO.class);
                }
            } catch (ExpiredJwtException ignored) {

            } catch (Exception e) {
                log.error("JWT Token解析失败，请检查", e);
            }
        }
        return null;
    }
}
