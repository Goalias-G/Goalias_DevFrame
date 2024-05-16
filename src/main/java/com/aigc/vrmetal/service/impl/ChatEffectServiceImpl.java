package com.aigc.vrmetal.service.impl;

import com.aigc.vrmetal.mapper.ChattingMapper;
import com.aigc.vrmetal.pojo.entity.ChatEffect;
import com.aigc.vrmetal.mapper.ChatEffectMapper;
import com.aigc.vrmetal.pojo.entity.Chatting;
import com.aigc.vrmetal.service.IChatEffectService;
import com.aigc.vrmetal.utils.SummaryUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.intellij.lang.annotations.RegExp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
public class ChatEffectServiceImpl extends ServiceImpl<ChatEffectMapper, ChatEffect> implements IChatEffectService {
    @Resource
    private ChatEffectMapper chatEffectMapper;
    private ChattingMapper chattingMapper;

    @Override
    public void compare(Long userId, Integer emo) {
        ChatEffect chatEffect = chatEffectMapper.selectById(userId);
        if (chatEffect == null) {
            chatEffectMapper.insert(new ChatEffect(userId.intValue(),emo.toString(),null,null,LocalDateTime.now()));
        }
        if (chatEffect!=null && Integer.parseInt(chatEffect.getEmoLow())<emo){
            chatEffect.setEmoHigh(emo.toString());
            if (emo -Integer.parseInt(chatEffect.getEmoLow())>3) {//有效治疗，用户很开心了
                QueryWrapper<Chatting> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("user_id",userId).ge("chat_time",LocalDateTime.now().minusDays(1L));
                List<Chatting> chattingList = chattingMapper.selectList(queryWrapper);
                StringBuilder stringBuilder=new StringBuilder();
                chattingList.forEach(chatting -> stringBuilder.append(chatting.getChatDetails()).append(","));
                String summary = SummaryUtil.summary(stringBuilder.toString());
                chatEffect.setDetails(summary);
            }
        }else {
            if (chatEffect != null) {
                chatEffect.setEmoHigh(chatEffect.getEmoLow());
                chatEffect.setEmoLow(emo.toString());
            }
        }
        chatEffectMapper.updateById(chatEffect);
    }
}
