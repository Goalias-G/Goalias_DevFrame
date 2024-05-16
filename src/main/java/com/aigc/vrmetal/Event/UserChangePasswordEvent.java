package com.aigc.vrmetal.Event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
public class UserChangePasswordEvent extends ApplicationEvent {
    private Object userId;

    public UserChangePasswordEvent(String userId) {
        super(new Object());
        this.userId = userId;

    }

}

