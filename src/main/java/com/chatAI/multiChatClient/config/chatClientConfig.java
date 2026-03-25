package com.chatAI.multiChatClient.config;

import com.chatAI.multiChatClient.advisor.tokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class chatClientConfig {

    @Primary
    @Bean
    public ChatClient ollamaOfficeChatClient(OllamaChatModel ollamaChatModel) {
        ChatClient.Builder chatClientBuilder = ChatClient.builder(ollamaChatModel).defaultAdvisors(new SimpleLoggerAdvisor());
        return chatClientBuilder.build();
    }

}
