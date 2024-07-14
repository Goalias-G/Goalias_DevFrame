package com.dev.model.controller;

import com.dev.model.pojo.vo.Result;
import com.dev.model.utils.MinIOUtil;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
@Api(tags = "测试控制器")
public class TestController {
    @Resource
    private MinIOUtil minIOUtil;
    @PostMapping("/1")
    public Result test1(@RequestParam String bucketName,@RequestParam String objectName,@RequestParam String targetFilePath){
        minIOUtil.downloadFile(bucketName,objectName,targetFilePath);
        return Result.success();
    }
}
