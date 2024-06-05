package com.dev.model.interceptor;

import com.dev.model.context.UserContext;
import com.dev.model.properties.JwtProperties;
import com.dev.model.utils.JwtUtil;

import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
       /* response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true"); //允许浏览器读取response的内容
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");// 允许HTTP请求的方法
        response.setHeader("Access-Control-Max-Age", "86400");// 设置请求preflight缓存的时间，单位 秒
        response.setHeader("Access-Control-Allow-Headers", "*");// 表明服务器支持所有头信息字段 */
        // 如果是OPTIONS则结束请求
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
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
            response.setStatus(401);
            return false;
        }
        if (StringUtils.hasText(userId.toString())) {
            response.setStatus(401);
            return false;
        }
        UserContext.setCurrentId(userId);
        return true;
    }

}
