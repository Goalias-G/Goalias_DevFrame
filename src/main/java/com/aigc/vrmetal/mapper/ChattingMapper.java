package com.aigc.vrmetal.mapper;

import com.aigc.vrmetal.pojo.dto.ChatPageQueryDto;
import com.aigc.vrmetal.pojo.entity.Chatting;
import com.aigc.vrmetal.pojo.vo.ChatVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gws
 * @since 2024-03-15
 */
public interface ChattingMapper extends BaseMapper<Chatting> {

    IPage<ChatVo> getTalks(IPage page, ChatPageQueryDto chatPageQueryDto);
}
