package com.aigc.vrmetal.controller;


import com.aigc.vrmetal.pojo.vo.Result;
import com.aigc.vrmetal.utils.EmoUtil;
import com.aigc.vrmetal.utils.GptUtil;
import com.aigc.vrmetal.utils.SummaryUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/test")
public class TestController {
    @PostMapping("emo1")
    public Result emo1(@RequestParam(required = false,value = "audio_file")MultipartFile audio_file){
        Integer emo = EmoUtil.predict(audio_file);
        return Result.success(emo);
    }

    @PostMapping("emo2")
    public Result emo2(@RequestParam(required = false,value = "audio_file")MultipartFile audio_file, @RequestParam(required = false,value = "video_file")MultipartFile video_file){
        Integer emo = EmoUtil.multipartPredict(audio_file, video_file);
        return Result.success(emo);
    }
    @PostMapping("gpt1")
    public Result gpt1(@RequestBody String question){
        String chat = GptUtil.chat(question);
        return Result.success(chat);
    }
    @PostMapping("gpt2")
    public Result gpt2(@RequestParam(required = false,value = "audio_file")MultipartFile audio_file){
        String transcribe = GptUtil.transcribe(audio_file);
        return Result.success(transcribe);
    }
    @PostMapping("summary")
    public Result summary(@RequestBody String text){
        String summary = SummaryUtil.summary(text);
        return Result.success(summary);
    }
}
