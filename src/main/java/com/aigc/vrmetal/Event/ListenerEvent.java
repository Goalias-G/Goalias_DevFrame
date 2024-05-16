package com.aigc.vrmetal.Event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class ListenerEvent {
    //@EventListener
    @TransactionalEventListener(UserChangePasswordEvent.class)//不影响原有业务，原本业务事务会及时提交。不会等待此拓展执行
    public void LoginEvent(UserChangePasswordEvent userChangePasswordEvent){
        System.out.println(userChangePasswordEvent.toString());
        System.out.println("开始执行事件处理。。。。");
    }
    @Async
    @EventListener({ UserChangePasswordEvent.class })
    public void logListener(UserChangePasswordEvent event) {
        System.out.println(System.currentTimeMillis());
        Instant instant = Instant.ofEpochMilli(event.getTimestamp());
        System.out.println( LocalDateTime.ofInstant(instant, ZoneId.systemDefault())+"收到事件:" + event);
        System.out.println("开始执行业务操作生成关键日志。用户userId为：" + event.getUserId());
        System.out.println(Thread.currentThread().getName());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT,value = { UserChangePasswordEvent.class })
    public void messageListener(UserChangePasswordEvent event) {
        System.out.println("收到事件:" + event);
        System.out.println("开始执行业务操作给用户发送短信。用户userId为：" + event.getUserId());
    }

}
