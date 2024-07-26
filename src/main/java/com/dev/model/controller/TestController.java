package com.dev.model.controller;

import com.dev.model.context.BizException;
import com.dev.model.pojo.vo.Result;
import com.dev.model.properties.ExceptionEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

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


}
