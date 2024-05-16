package com.aigc.vrmetal.controller;


import com.aigc.vrmetal.context.BaseContext;
import com.aigc.vrmetal.pojo.dto.ChatPageQueryDto;
import com.aigc.vrmetal.pojo.entity.Chatting;
import com.aigc.vrmetal.pojo.vo.ChatQueryVo;
import com.aigc.vrmetal.pojo.vo.Result;
import com.aigc.vrmetal.service.IChatEffectService;
import com.aigc.vrmetal.service.IChattingService;
import com.aigc.vrmetal.service.impl.ChatEffectServiceImpl;
import com.aigc.vrmetal.utils.EmoUtil;
import com.aigc.vrmetal.utils.GptUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gws
 * @since 2024-03-11
 */
@RestController
@RequestMapping("/chatting")
@Api(tags = "交谈记录接口")
public class ChattingController {
    @Resource
    private IChattingService chattingService;
    @Resource
    private IChatEffectService chatEffectService;
    @GetMapping("talks")
    @ApiOperation("分页查询交谈接口")
    public Result<ChatQueryVo> talks(ChatPageQueryDto chatPageQueryDto){
        ChatQueryVo chatVos=chattingService.getTalks(chatPageQueryDto);
        return Result.success(chatVos);
    }
    @PostMapping("chat")
    @ApiOperation("用户聊天接口")
    public Result chat(@RequestBody(required = false) String question, @RequestParam(required = false,value = "audio_file") MultipartFile audio_file, @RequestParam(required = false,value = "video_file") MultipartFile video_file){
        String result = null;
        Long userId = BaseContext.getCurrentId();
        if (question!=null && !question.isEmpty()){
            chattingService.save(new Chatting(userId.intValue(),question,LocalDateTime.now(),0));
            result= GptUtil.chat(question);
        }
        if (!audio_file.isEmpty()) {
            Integer emo;
            if (!video_file.isEmpty())
                emo = EmoUtil.multipartPredict(audio_file, video_file);
            else
                emo=EmoUtil.predict(audio_file);
            String transcribe = GptUtil.transcribe(audio_file);
            chattingService.save(new Chatting(userId.intValue(), transcribe, LocalDateTime.now(), emo));
            chatEffectService.compare(userId, emo);
            result = GptUtil.chat(transcribe);
        }
        return Result.success(result);
    }
    @PostMapping("chart/{id}")
    @ApiOperation("单个用户情绪值10天曲线")
    public Result<List<Integer>> chart(@PathVariable String id){
        LocalDate preTenDate = LocalDate.parse(LocalDateTime.now().minusHours(24 * 9).format(DateTimeFormatter.ISO_LOCAL_DATE));
        QueryWrapper<Chatting> chattingQueryWrapper = new QueryWrapper<>();
        chattingQueryWrapper.gt("chat_time",preTenDate)
                .eq("user_id",Integer.parseInt(id))
                .orderByAsc("chat_time");
        List<Chatting> list = chattingService.list(chattingQueryWrapper);
        List<Integer> days=new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        for (int j = 0; j < 10; j++) {
            for (Chatting chatting : list) {
                if (chatting.getChatTime().toLocalDate().isEqual(preTenDate.plusDays(j))){
                    days.set(j, chatting.getChatEmo());
                    break;
                }else if (j==0) days.set(j, 0);
                     else days.set(j, days.get(j-1));
            }
        }
        return Result.success(days);
    }
}
