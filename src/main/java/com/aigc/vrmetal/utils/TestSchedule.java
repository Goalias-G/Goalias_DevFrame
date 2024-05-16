package com.aigc.vrmetal.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestSchedule {

    @Scheduled(cron = "0/2 * * * * ?")
    public void test() {
        System.out.println("执行了。。。。。。");
    }

}
