package com.dev.model.controller;


import com.dev.model.context.exception.BizException;
import com.dev.model.context.properties.ExceptionEnum;
import com.dev.model.pojo.Result;
import com.dev.model.pojo.entity.User;
import com.dev.model.service.IUserService;
import com.dev.model.service.RedisService;
import com.dev.model.utils.EmailUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    public Result<String> test(){
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
        emailUtil.sendHtmlMail("*********@qq.com", "goalias邮件", emailUtil.findPasswordTemplate("xxx", "123321", "http://127.0.0.1/"));
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
