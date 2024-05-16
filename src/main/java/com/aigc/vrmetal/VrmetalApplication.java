package com.aigc.vrmetal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.aigc.vrmetal.mapper")
@EnableSwagger2
@EnableAsync
@EnableScheduling
public class VrmetalApplication {

    public static void main(String[] args) {
        SpringApplication.run(VrmetalApplication.class, args);
    }

}
