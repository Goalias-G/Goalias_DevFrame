package com.dev.model.controller;


import com.dev.model.pojo.Result;
import dev.langchain4j.data.message.ChatMessage;
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


    @GetMapping("hello")
    public Result test(String text){
        ChatMessage chatMessage = new UserMessage(text);
        ChatRequest chatRequest = new ChatRequest.Builder().messages(chatMessage)
                .build();
        ChatResponse chatResponse = openAiChatModel.chat(chatRequest);
        return Result.success(chatResponse.aiMessage().text());
    }

    @GetMapping(value = "/sse", produces = "text/event-stream;charset=UTF-8")
    public SseEmitter chat() throws IOException {
        SseEmitter sseEmitter = new SseEmitter();
        sseEmitter.send("hello");
        sseEmitter.complete();
        return sseEmitter;
    }
}
