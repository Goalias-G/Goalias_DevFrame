package com.dev.model.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Schedule {

//    @Scheduled(cron = "0/2 * * * * ?")
    public void doSomething() {
        System.out.println("执行了。。。。。。");
    }

}
