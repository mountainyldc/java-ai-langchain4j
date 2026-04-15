package com.atguigu.java.ai.langchain4j.config;

import com.atguigu.java.ai.langchain4j.store.MongoChatMemoryStore;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration           // 标记这是一个Spring配置类
public class XiaozhiAgentConfig {

    // 注入MongoChatMemoryStore
    @Autowired
    private MongoChatMemoryStore mongoChatMemoryStore;

    // 注册成Spring Bean，方法名就是Bean的名字
    @Bean
    public ChatMemoryProvider chatMemoryProviderXiaozhi(){

        // lambda表达式：输入是memoryId，输出是ChatMemory
        return memoryId ->
            MessageWindowChatMemory.builder()
                    // MessageWindowChatMemory 是 ChatMemory 的实现类
                    .id(memoryId)                          // 设置会话ID
                    .maxMessages(20)                       // 最多保存20条消息
                    .chatMemoryStore(mongoChatMemoryStore) // 绑定MongoDB持久化
                    .build();
    }

    @Bean
    ContentRetriever contentRetrieverXiaozhi() {
        //使用FileSystemDocumentLoader读取指定目录下的知识库文档
        //并使用默认的文档解析器对文档进行解析
        Document document1 = FileSystemDocumentLoader.loadDocument("E:/knowledge/医院信息.md");
        Document document2 = FileSystemDocumentLoader.loadDocument("E:/knowledge/科室信息.md");
        Document document3 = FileSystemDocumentLoader.loadDocument("E:/knowledge/神经内科.md");
        List<Document> documents = Arrays.asList(document1, document2, document3);

        //使用内存向量存储
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        //使用默认的文档分割器
        EmbeddingStoreIngestor.ingest(documents, embeddingStore);

        //从嵌入存储（EmbeddingStore）里检索和查询内容相关的信息
        return EmbeddingStoreContentRetriever.from(embeddingStore);
    }
}
