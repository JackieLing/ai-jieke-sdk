package com.jieke.coze.test;

import com.google.gson.Gson;
import com.jieke.coze.client.JieKeAiClient;
import com.jieke.coze.model.Message;
import com.jieke.coze.model.response.ChatResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
@Slf4j
public class CozeClientTest {
    private static final String TOKEN = "pat_NlLVqAwVCYXRyBdOFg111IUiMI9H4bRFzYdaV9ntkSC8K2F0HYufpUjbjkTdufax";

    public static void main(String[] args) throws IOException, InterruptedException {
        // 1. 初始化聊天
        JieKeAiClient chatClient = new JieKeAiClient.Builder(TOKEN)
                .botId("7485191777520009228")
                .userId("123123")
                .additionalMessage(Message.builder()
                        .contentType("text")
                        .content("人工智能")
                        .role("user")
                        .type("question")
                        .build())
                .build();
        String chatResponse = chatClient.initializeChat();
        Gson gson = new Gson();
        ChatResponse response = gson.fromJson(chatResponse, ChatResponse.class);

        // 获取 id
        String id = response.getData().getId();
        String conversationId = response.getData().getConversation_id();
        log.info("智能体SDK收到参数---->chatId:{}",id);
        log.info("智能体SDK收到参数---->conversation_Id:{}",conversationId);

        Thread.sleep(6000);

        // 2. 查看对话详情
        JieKeAiClient conversationClient = new JieKeAiClient.Builder(TOKEN)
                .conversationId(conversationId)
                .chatId(id)
                .build();
        String conversationResponse = conversationClient.retrieveChat();
        System.out.println("对话详情响应: " + conversationResponse);

//         3. 查看消息列表
        String messageListResponse = conversationClient.getMessageList();
        System.out.println("消息列表响应: " + messageListResponse);
    }
}

