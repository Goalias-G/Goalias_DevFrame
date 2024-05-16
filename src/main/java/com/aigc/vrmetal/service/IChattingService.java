package com.aigc.vrmetal.service;

import com.aigc.vrmetal.pojo.dto.ChatPageQueryDto;
import com.aigc.vrmetal.pojo.entity.Chatting;
import com.aigc.vrmetal.pojo.vo.ChatQueryVo;
import com.aigc.vrmetal.pojo.vo.ChatVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gws
 * @since 2024-03-11
 */
public interface IChattingService extends IService<Chatting> {

    ChatQueryVo getTalks(ChatPageQueryDto chatPageQueryDto);
}
