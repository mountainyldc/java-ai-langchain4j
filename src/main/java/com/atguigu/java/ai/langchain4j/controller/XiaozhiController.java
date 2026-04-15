package com.atguigu.java.ai.langchain4j.controller;

import com.atguigu.java.ai.langchain4j.assistant.XiaoZhiAgent;
import com.atguigu.java.ai.langchain4j.bean.ChatForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "硅谷小智")
@RestController
@RequestMapping("/xiaozhi")
public class XiaozhiController {
    @Autowired
    private XiaoZhiAgent xiaoZhiAgent;

    @Operation(summary = "对话")
    @PostMapping("/chat")
    public String chat(@RequestBody ChatForm chatForm){
        // 参数校验，防止空消息
        if (chatForm.getMessage() == null || chatForm.getMessage().trim().isEmpty()) {
            return "消息不能为空";
        }
        return xiaoZhiAgent.chat(chatForm.getMemoryId(), chatForm.getMessage());
    }
}
