package com.dev.model;

import com.dev.model.utils.MinIOUtil;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
public class TestSomething {
    @Resource
    private MinioClient minioClient;
    @Test
    public void test(){
        try {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket("testbucket").build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
