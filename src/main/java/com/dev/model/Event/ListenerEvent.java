package com.dev.model.Event;

import com.dev.model.pojo.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class ListenerEvent {
    @Async
    @EventListener(condition = "#userDto.id != null ")
    public void logListener(@NotNull UserDto userDto) {
        System.out.println( LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-dd-MM"))+"收到事件:" + userDto);
        System.out.println("开始执行业务操作生成关键日志。用户userId为：" + userDto.getId());
        System.out.println(Thread.currentThread().getName());
    }
}
