package com.atguigu.java.ai.langchain4j.store;

import com.atguigu.java.ai.langchain4j.bean.ChatMessages;

import dev.langchain4j.data.message.ChatMessage;

import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;

import dev.langchain4j.store.memory.chat.ChatMemoryStore;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.query.Criteria;

import org.springframework.data.mongodb.core.query.Query;

import org.springframework.data.mongodb.core.query.Update;

import org.springframework.stereotype.Component;

import java.util.LinkedList;

import java.util.List;
@Component  // 把这个类交给Spring管理，Spring会自动创建一个Bean实例，你可以在其他类里用 @Autowired 注入它。
public class MongoChatMemoryStore implements ChatMemoryStore {

    @Autowired          // 依赖注入MongoDB操作工具
    private MongoTemplate mongoTemplate;

    @Override           // 重写读取聊天记录方法
    public List<ChatMessage> getMessages(Object memoryId) {
        // 1.创建查询条件：memoryId字段等于传入的memoryId
        Criteria criteria = Criteria.where("memoryId").is(memoryId);

        // 2. 把条件包装成查询对象
        Query query = new Query(criteria);

        // 3.去MongoDB查，把结果映射成ChatMessages对象
        ChatMessages chatMessages = mongoTemplate.findOne(query,ChatMessages.class);

        // 4. 如果没查到（新人第一次聊天），返回空列表
        if(chatMessages == null){
            return new LinkedList<>();
        }
        // 5.取出content字段（JSON字符串）
        String contentJson = chatMessages.getContent();

        // 6.把JSON转回ChatMessage列表
        return ChatMessageDeserializer.messagesFromJson(contentJson);
    }

    @Override         // 更新聊天记忆
    public void updateMessages(Object memoryId, List<ChatMessage> list) {
        // 1.同样先查条件
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query = new Query(criteria);

        // 2.创建更新对象
        Update update = new Update();

        // 3. 把ChatMessage列表转成JSON字符串，存入content字段
        update.set("content",ChatMessageSerializer.messagesToJson(list));

        // 4.执行更新/插入操作
        mongoTemplate.upsert(query,update,ChatMessages.class);
    }

    @Override        // 删除聊天记录
    public void deleteMessages(Object memoryId) {
        // 1.同样先查条件
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query = new Query(criteria);

        // 2.删除符合条件的所有文档
        mongoTemplate.remove(query, ChatMessages.class);
    }
}