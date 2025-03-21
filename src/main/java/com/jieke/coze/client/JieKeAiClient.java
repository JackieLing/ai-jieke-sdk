package com.jieke.coze.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieke.coze.exception.CozeException;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class JieKeAiClient {
    private static final String BASE_URL = "https://api.coze.cn/v3";
    private final String token;
    private String conversationId;
    private String chatId;
    private String botId;
    private String userId;
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    private JieKeAiClient(Builder builder) {
        this.token = builder.token;
        this.conversationId = builder.conversationId;
        this.chatId = builder.chatId;
        this.botId = builder.botId;
        this.userId = builder.userId;
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    // 初始化聊天
    public String initializeChat() throws IOException {
        if (botId == null || userId == null) {
            throw new CozeException("botId 和 userId 不能为空");
        }

        // 创建JSON格式的请求体
        String jsonBody = objectMapper.writeValueAsString(new HashMap<String, String>() {{
            put("bot_id", botId);
            put("user_id", userId);
        }});

        RequestBody requestBody = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            jsonBody
        );

        Request request = new Request.Builder()
                .url(BASE_URL + "/chat")
                .addHeader("Authorization", "Bearer " + token)
                .addHeader("Content-Type", "application/json")
                .post(requestBody)
                .build();

        return executeRequest(request);
    }

    // 获取聊天详情
    public String retrieveChat() throws IOException {
        if (conversationId == null || chatId == null) {
            throw new CozeException("conversationId 和 chatId 不能为空");
        }

        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + "/chat/retrieve").newBuilder()
                .addQueryParameter("conversation_id", conversationId)
                .addQueryParameter("chat_id", chatId);

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .addHeader("Authorization", "Bearer " + token)
                .get()
                .build();

        return executeRequest(request);
    }

    // 获取消息列表
    public String getMessageList() throws IOException {
        if (conversationId == null || chatId == null) {
            throw new CozeException("conversationId 和 chatId 不能为空");
        }

        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + "/chat/message/list").newBuilder()
                .addQueryParameter("conversation_id", conversationId)
                .addQueryParameter("chat_id", chatId);

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .addHeader("Authorization", "Bearer " + token)
                .get()
                .build();

        return executeRequest(request);
    }

    private String executeRequest(Request request) throws IOException {
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new CozeException("API请求失败: " + response.code());
            }
            ResponseBody responseBody = response.body();
            return responseBody != null ? responseBody.string() : null;
        }
    }

    public static class Builder {
        private final String token;
        private String conversationId;
        private String chatId;
        private String botId;
        private String userId;

        public Builder(String token) {
            this.token = token;
        }

        public Builder conversationId(String conversationId) {
            this.conversationId = conversationId;
            return this;
        }

        public Builder chatId(String chatId) {
            this.chatId = chatId;
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

        public JieKeAiClient build() {
            if (token == null) {
                throw new IllegalArgumentException("token 不能为空");
            }
            return new JieKeAiClient(this);
        }
    }
}