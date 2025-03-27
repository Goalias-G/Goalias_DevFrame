package com.dev.model.schedule;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduleTask {

    @Scheduled(cron = "0 0 6 ? * * ")
    public void doSomething() {
        System.out.println("执行定时任务");
    }

}
