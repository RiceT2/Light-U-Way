package com.xiaoyouxing.rag;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 参考 TravelAssistant 的向量存储配置，提供后续 RAG 增强入口。
 */
@Configuration
public class TravelVectorStoreConfig {

    @Bean
    public VectorStore travelAssistantVectorStore(EmbeddingModel embeddingModel) {
        return SimpleVectorStore.builder(embeddingModel).build();
    }
}
