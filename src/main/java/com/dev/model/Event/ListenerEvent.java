package com.dev.model.Event;

import com.dev.model.pojo.dto.UserDto;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class ListenerEvent {
    @Async
    @EventListener(condition = "#userDto.id != null ")
    public void logListener(UserDto userDto) {
        System.out.println( LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-dd-MM"))+"收到事件:" + userDto);
        System.out.println("开始执行业务操作生成关键日志。用户userId为：" + userDto.getId());
        System.out.println(Thread.currentThread().getName());
    }
}
