package com.dev.model.Event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class UserChangePasswordEvent extends ApplicationEvent {
    private Object userId;

    public UserChangePasswordEvent(String userId) {
        super(new Object());
        this.userId = userId;

    }

}

