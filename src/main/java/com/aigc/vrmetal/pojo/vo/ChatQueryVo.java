package com.aigc.vrmetal.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatQueryVo {
    private long total;
    private List<ChatVo> chatVoList;
}
