package com.jieke.coze.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieke.coze.exception.CozeException;
import com.jieke.coze.model.Message;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class JieKeAiClient {
    private static final String BASE_URL = "https://api.coze.cn/v3";
    private final String token;
    private String conversationId;
    private String chatId;
    private String botId;
    private String userId;
    // 删除 query 字段
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    // 在类的字段部分添加
    private final List<Message> additionalMessages;

    private JieKeAiClient(Builder builder) {
        this.token = builder.token;
        this.conversationId = builder.conversationId;
        this.chatId = builder.chatId;
        this.botId = builder.botId;
        this.userId = builder.userId;
        this.additionalMessages = builder.additionalMessages;
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

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("bot_id", botId);
        requestMap.put("user_id", userId);
        if (additionalMessages != null && !additionalMessages.isEmpty()) {
            List<Map<String, String>> messages = additionalMessages.stream()
                    .map(msg -> {
                        Map<String, String> messageMap = new HashMap<>();
                        messageMap.put("content_type", msg.getContentType());
                        messageMap.put("content", msg.getContent());
                        messageMap.put("role", msg.getRole());
                        messageMap.put("type", msg.getType());
                        return messageMap;
                    })
                    .collect(Collectors.toList());
            requestMap.put("additional_messages", messages);
        }

        String jsonBody = objectMapper.writeValueAsString(requestMap);

        // 修改请求头和请求体的设置方式
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(mediaType, jsonBody);

        Request request = new Request.Builder()
                .url(BASE_URL + "/chat")
                .addHeader("Authorization", "Bearer " + token)
                .addHeader("Accept", "application/json")
                .post(requestBody)
                .build();

        // 打印请求信息，方便调试
        System.out.println("Request URL: " + request.url());
        System.out.println("Request Headers: " + request.headers());
        System.out.println("Request Body: " + jsonBody);

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
        private final List<Message> additionalMessages = new ArrayList<>();  // 添加这行
        
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

        public Builder additionalMessage(Message message) {
            if (message != null) {
                this.additionalMessages.add(message);
            }
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