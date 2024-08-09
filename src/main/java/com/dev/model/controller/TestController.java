package com.dev.model.controller;

import com.dev.model.context.exception.BizException;
import com.dev.model.pojo.Result;
import com.dev.model.context.properties.ExceptionEnum;
import com.dev.model.service.RedisService;
import com.dev.model.utils.EmailUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private EmailUtil emailUtil;
    @Resource
    private RedisService redisService;


    @GetMapping("test")
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
        emailUtil.sendHtmlMail("986891392@qq.com", "love", emailUtil.findPasswordTemplate("lwx小弟", "5201314", "http://182.92.244.97/"));
        return Result.success("发送成功");
    }

    @GetMapping("redis")
    public Result<String> redis(){
        redisService.set("aa", "bb");
        return Result.success("发送成功");
    }
}
