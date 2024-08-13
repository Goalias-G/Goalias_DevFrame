package com.dev.model.canal;


import lombok.Getter;

@Getter
public enum CanalHandleEnum {

    USER("userCanalHandleServiceImpl");


    private final String handler;

    CanalHandleEnum(String handler)
    {
        this.handler=handler;
    }

}
