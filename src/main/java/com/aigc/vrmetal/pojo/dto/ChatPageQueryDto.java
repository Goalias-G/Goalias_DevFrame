package com.aigc.vrmetal.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatPageQueryDto implements Serializable {
    public ChatPageQueryDto() {
        this.page=1;
        this.pageSize=5;
    }

    private int page;

    private int pageSize;
    private String chatEmo;
}
