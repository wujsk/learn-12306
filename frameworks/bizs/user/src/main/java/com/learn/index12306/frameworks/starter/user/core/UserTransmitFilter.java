package com.learn.index12306.frameworks.starter.user.core;

import com.learn.index12306.frameworks.starter.bases.constant.UserConstant;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URLDecoder;

/**
 * @author: cyy
 * @create: 2025-03-12 08:24
 **/
public class UserTransmitFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String userId = request.getHeader(UserConstant.USER_ID_KEY);
        if (StringUtils.hasText(userId)) {
            String username = request.getHeader(UserConstant.USER_NAME_KEY);
            String realname = request.getHeader(UserConstant.REAL_NAME_KEY);
            if (StringUtils.hasText(username)) {
                username = URLDecoder.decode(username, "UTF-8");
            }
            if (StringUtils.hasText(realname)) {
                realname = URLDecoder.decode(realname, "UTF-8");
            }
            String token = request.getHeader(UserConstant.USER_TOKEN_KEY);
            UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                    .userId(Long.valueOf(userId))
                    .username(username)
                    .realname(realname)
                    .token(token)
                    .build();
            UserContext.setUser(userInfoDTO);
        }
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            UserContext.removeUser();
        }
    }
}
