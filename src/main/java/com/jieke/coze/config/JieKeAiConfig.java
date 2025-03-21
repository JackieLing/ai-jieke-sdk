package com.jieke.coze.config;


public class JieKeAiConfig {
    private final String token;
    private final String botId;
    private final String userId;
    private final String apiBaseUrl;

    private JieKeAiConfig(Builder builder) {
        this.token = builder.token;
        this.botId = builder.botId;
        this.userId = builder.userId;
        this.apiBaseUrl = builder.apiBaseUrl != null ? builder.apiBaseUrl : "https://api.coze.cn";
    }

    public static class Builder {
        private String token;
        private String botId;
        private String userId;
        private String apiBaseUrl;

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder botId(String botId) {
            this.botId = botId;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder apiBaseUrl(String apiBaseUrl) {
            this.apiBaseUrl = apiBaseUrl;
            return this;
        }

        public JieKeAiConfig build() {
            if (token == null || botId == null) {
                throw new IllegalArgumentException("token 和 botId 不能为空");
            }
            return new JieKeAiConfig(this);
        }
    }

    // Getters
    public String getToken() { return token; }
    public String getBotId() { return botId; }
    public String getUserId() { return userId; }
    public String getApiBaseUrl() { return apiBaseUrl; }
}