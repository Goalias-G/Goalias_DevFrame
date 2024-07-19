package com.dev.model;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.dev.model.mapper")
@EnableCaching
public class DevModelApplication {
    public static void main(String[] args) {
        SpringApplication.run(DevModelApplication.class, args);
        System.out.println("   ____  ___    _    _     ___    _    ____  \n" +
                "  / ___|/ _ \\  / \\  | |   |_ _|  / \\  / ___| \n" +
                " | |  _| | | |/ _ \\ | |    | |  / _ \\ \\___ \\ \n" +
                " | |_| | |_| / ___ \\| |___ | | / ___ \\ ___) |\n" +
                "  \\____|\\___/_/   \\_\\_____|___/_/   \\_\\____/ \n" +
                "                                             ");
    }
}
