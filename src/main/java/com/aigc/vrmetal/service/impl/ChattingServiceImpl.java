package com.aigc.vrmetal.service.impl;

import com.aigc.vrmetal.pojo.dto.ChatPageQueryDto;
import com.aigc.vrmetal.pojo.entity.Chatting;
import com.aigc.vrmetal.mapper.ChattingMapper;
import com.aigc.vrmetal.pojo.vo.ChatQueryVo;
import com.aigc.vrmetal.pojo.vo.ChatVo;
import com.aigc.vrmetal.service.IChattingService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gws
 * @since 2024-03-11
 */
@Service
@Primary
public class ChattingServiceImpl extends ServiceImpl<ChattingMapper, Chatting> implements IChattingService {
    @Resource
    private ChattingMapper chattingMapper;

    @Override
    public ChatQueryVo getTalks(ChatPageQueryDto chatPageQueryDto) {
        IPage page=new Page(chatPageQueryDto.getPage(), chatPageQueryDto.getPageSize());
        IPage<ChatVo> chatVoIPage=chattingMapper.getTalks(page,chatPageQueryDto);
        ChatQueryVo chatQueryVo = ChatQueryVo.builder().total(chatVoIPage.getTotal())
                .chatVoList(chatVoIPage.getRecords()).build();
        return chatQueryVo;
    }
}
