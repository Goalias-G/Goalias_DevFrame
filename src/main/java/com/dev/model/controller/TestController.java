package com.dev.model.controller;


import com.dev.model.context.exception.BizException;
import com.dev.model.context.properties.ExceptionEnum;
import com.dev.model.pojo.Result;

import com.dev.model.service.IUserService;
import com.dev.model.service.RedisService;
import com.dev.model.utils.EmailUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Resource
    private EmailUtil emailUtil;
    @Resource
    private IUserService userService;
    @Resource
    private RedisService redisService;


    @GetMapping("hello")
    public Result<String> test(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = new Cookie("name", "goalias");
        response.addCookie(cookie);
        return Result.success("hello goalias");
    }
    @GetMapping("exception1")
    public Result<String> test1(){
        throw new BizException(ExceptionEnum.SERVER_BUSY);
    }
    @GetMapping("exception2")
    public Result<String> test2(){
        throw new NullPointerException("自定义空指针");
    }


    @GetMapping("email")
    public Result<String> email(){
        emailUtil.sendSimpleMail("gao0831mail@163.com", "10.25测试", "test");
        return Result.success("发送成功");
    }

    @GetMapping("redis")
    public Result<String> redis(){
        redisService.set("test", "aa");
        return Result.success("发送成功");
    }

    @GetMapping("canal")
    public Result<String> canal(){
//        userService.save(new User("goalias","123456"));
        return Result.success("发送成功");
    }
}
