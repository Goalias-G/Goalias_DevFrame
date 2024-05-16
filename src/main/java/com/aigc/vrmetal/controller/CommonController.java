package com.aigc.vrmetal.controller;

import com.aigc.vrmetal.pojo.vo.Result;
import com.aigc.vrmetal.properties.AliOssProperties;

import com.aigc.vrmetal.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
@Slf4j
@Api(tags = "通用接口")
public class CommonController {
    @Resource
    private AliOssProperties aliOssProperties;


        @PostMapping("/upload")
        @ApiOperation("文件上传")
        public Result<String> upload(MultipartFile file){
            AliOssUtil aliOssUtil=new AliOssUtil(aliOssProperties.getEndpoint(), aliOssProperties.getAccessKeyId(), aliOssProperties.getAccessKeySecret(), aliOssProperties.getBucketName());
            try {
                String originalFilename = file.getOriginalFilename();
                String substring = null;
                if (originalFilename != null) {
                    substring = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                String objectName = UUID.randomUUID() + substring;
                String filePath = aliOssUtil.upload(file.getBytes(), objectName);
                return Result.success(filePath);
            } catch (IOException e) {
                log.info("文件上传失败：{}",e);
                e.printStackTrace();
            }
            return Result.error("文件上传失败");
        }

    }

