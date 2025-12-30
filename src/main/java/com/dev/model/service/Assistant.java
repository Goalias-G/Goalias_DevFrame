package com.dev.model.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(chatMemory = "chatMemory", chatModel = "openAiChatModel",
        wiringMode = AiServiceWiringMode.EXPLICIT, chatMemoryProvider = "chatMemoryProvider")
public interface Assistant {
    @SystemMessage("你是我的好朋友，总是以搞怪，幽默的语言风格回答问题")
    String chat(@MemoryId String memoryId, @UserMessage String userInput);

    @SystemMessage("你是我的好朋友，我是{{username}},我的年龄是{{age}},总是以搞怪，幽默的语言风格回答问题，今天是{{current_date}}")
    String chatIdentifier(@MemoryId String memoryId, @UserMessage String userInput,
                          @V("username") String username, @V("age") Integer age);
}
