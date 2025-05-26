package com.dev.model.interceptor;

import com.dev.model.context.UserContext;
import com.dev.model.context.constant.HttpStatus;
import com.dev.model.context.properties.JwtProperties;
import com.dev.model.utils.JwtUtil;

import io.jsonwebtoken.Claims;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;




@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 如果是OPTIONS则结束请求
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpStatus.NO_CONTENT);
            return false;
        }
        if (!(handler instanceof HandlerMethod)) {//是否为动态方法
            return true;
        }
        String token = request.getHeader(jwtProperties.getUserTokenName());

        Long userId;
        try {
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            userId = Long.valueOf(claims.get("userId").toString());
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED);
            return false;
        }
        if (StringUtils.hasText(userId.toString())) {
            response.setStatus(HttpStatus.UNAUTHORIZED);
            return false;
        }
        UserContext.setCurrentId(userId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.removeCurrentId();
    }
}
