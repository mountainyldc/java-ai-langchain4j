package com.atguigu.java.ai.langchain4j.assistant;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

// 初级智能体
@AiService(
        wiringMode = EXPLICIT,
        chatModel = "qwenChatModel",
        chatMemory = "chatMemory"
)
public interface MemoryChatAssistant {

    // 用户提示词，@V注解标识占位符
    @UserMessage("你是我的好朋友，请用广东话回答问题，并且添加一些表情符号。 {{message}}")
    String chat(@V("message") String message);


}
