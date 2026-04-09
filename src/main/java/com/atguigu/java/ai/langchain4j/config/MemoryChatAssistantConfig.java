package com.atguigu.java.ai.langchain4j.config;


import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration                    // 1. 声明：这是一个“零件加工厂”
public class MemoryChatAssistantConfig {
    @Bean                         // 2. 声明：我要生产一个叫 "chatMemory" 的零件（方法名就是零件名）
    public ChatMemory chatMemory(){
        // 生产过程：创建一个能记住最近 10 条对话的“滑动窗口”记忆盒
        return MessageWindowChatMemory.withMaxMessages(10);
    }
}
