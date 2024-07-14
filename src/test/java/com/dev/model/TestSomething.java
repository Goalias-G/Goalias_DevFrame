package com.dev.model;

import com.dev.model.pojo.dto.UserDto;
import io.minio.MinioClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import javax.annotation.Resource;


@SpringBootTest
public class TestSomething {
    @Resource
    private MinioClient minioClient;
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;
    @Test
    public void test(){
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setPassword("<PASSWORD>");
        applicationEventPublisher.publishEvent(userDto);
    }
}
