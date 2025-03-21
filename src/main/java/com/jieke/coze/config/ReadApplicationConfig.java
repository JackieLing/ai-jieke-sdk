package com.jieke.coze.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class ReadApplicationConfig {
    @Value("${coze.token}")
    private String token;

    @Value("${coze.bot-id}")
    private String botId;

    @Value("${coze.user-id}")
    private String userId;

    public String getToken() {
        return token;
    }

    public String getBotId() {
        return botId;
    }

    public String getUserId() {
        return userId;
    }
}