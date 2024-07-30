package com.dev.model.config;

import com.dev.model.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


import javax.annotation.Resource;


@Configuration
@Slf4j
public class WebMvcConfiguration implements WebMvcConfigurer {
    static final String ORIGINS[] = new String[] { "GET", "POST", "PUT", "DELETE" , "OPTIONS"};

    @Resource
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
//                .addPathPatterns("/**/**")
//                .excludePathPatterns("/user/login","/user/sendCode","/manage/login","doctor/login")
                .excludePathPatterns("/**/**")
                .order(0);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedOrigins("/**")
                .allowedMethods(ORIGINS)
                .allowCredentials(true)
                .maxAge(3600);
    }
}
