package com.atguigu.java.ai.langchain4j.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("chat_messages") // 告诉程序这个类对应 MongoDB 里一个叫 chat_messages 的集合（Collection）”。
public class ChatMessages {
    //唯一标识，映射到 MongoDB 文档的 _id 字段
    @Id                             // 标记这个字段是主键
    private ObjectId messageId;

    private String userId;

    private String content; //存储当前聊天记录列表的json字符串



}