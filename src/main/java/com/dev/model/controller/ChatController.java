package com.dev.model.controller;


import com.dev.model.pojo.Result;
import com.dev.model.service.Assistant;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Resource
    private OpenAiChatModel openAiChatModel;
    @Resource
    private Assistant assistant;


    @GetMapping("/simpleChat")
    public Result test(String text){
        ChatMessage chatMessage = new UserMessage(text);
        SystemMessage systemMessage = new SystemMessage("你是我的好朋友，总是以搞怪，幽默的语言风格回答问题");
        ChatRequest chatRequest = new ChatRequest.Builder().messages(chatMessage,systemMessage)
                .build();
        ChatResponse chatResponse = openAiChatModel.chat(chatRequest);
        return Result.success(chatResponse.aiMessage().text());
    }

    @GetMapping("/memoryChat")
    public Result memoryChat(String text){
        String answer = assistant.chat("213",text);
        return Result.success(answer);
    }

    @GetMapping(value = "/sse", produces = "text/event-stream;charset=UTF-8")
    public SseEmitter chat() throws IOException {
        SseEmitter sseEmitter = new SseEmitter();
        sseEmitter.send("hello");
        sseEmitter.complete();
        return sseEmitter;
    }
}
