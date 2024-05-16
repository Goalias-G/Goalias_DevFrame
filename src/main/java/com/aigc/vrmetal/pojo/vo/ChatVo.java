package com.aigc.vrmetal.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatVo {
    private Integer userId;

    private String chatDetails;

    private LocalDateTime chatTime;

    private String chatEmo;
    private String username;

}
