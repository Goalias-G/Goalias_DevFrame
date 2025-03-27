package com.dev.model.controller;


import com.dev.model.context.exception.BizException;
import com.dev.model.context.exception.ExceptionEnum;
import com.dev.model.pojo.Result;

import com.dev.model.service.IUserService;
import com.dev.model.service.RedisService;
import com.dev.model.utils.EmailUtil;
import com.dev.model.utils.ExcelUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private EmailUtil emailUtil;
    @Resource
    private IUserService userService;
    @Resource
    private RedisService redisService;


    @GetMapping("hello")
    public Result test(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = new Cookie("name", "goalias");
        response.addCookie(cookie);
        return Result.success("hello goalias");
    }
    @GetMapping("exception")
    public Result test1(){
        throw new BizException(ExceptionEnum.RESOURCE_NOT_EXIST);
    }
    @PostMapping("exportExcel")
    public void excel(HttpServletResponse resp) {
        List<String> headers = List.of("姓名", "年龄", "地址");
        List<List<String>> data = List.of(
                List.of("张三", "18", "北京"),
                List.of("李四", "19", "上海"),
                List.of("王五", "20", "广州")
        );
        try {
            byte[] bytes = ExcelUtil.exportToExcel("test", headers, data);
            resp.setHeader("Content-Disposition", "attachment;filename=" + "test.xlsx");
            resp.setCharacterEncoding("utf-8");
            resp.setContentType("application/octet-stream");
            resp.setContentLength(bytes.length);
            resp.getOutputStream().write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @GetMapping("email")
    public Result email(){
        emailUtil.sendSimpleMail("gao0831mail@163.com", "10.25测试", "test");
        return Result.success("发送成功");
    }

    @GetMapping("redis")
    public Result redis(){
        redisService.set("test", "aa");
        return Result.success("发送成功");
    }

    @GetMapping("canal")
    public Result canal(){
//        userService.save(new User("goalias","123456"));
        return Result.success("发送成功");
    }
    @GetMapping(value = "/sse", produces = "text/event-stream;charset=UTF-8")
    public SseEmitter chat() throws IOException {
        SseEmitter sseEmitter = new SseEmitter();
        sseEmitter.send("hello");
        sseEmitter.complete();
        return sseEmitter;
    }
}
