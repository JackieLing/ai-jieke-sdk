package com.jieke.coze.test;

import com.jieke.coze.client.JieKeAiClient;
import java.io.IOException;

public class CozeClientTest {
    private static final String TOKEN = "pat_JroqVu638bX1O2ElWlfFc772IEOM8RuHR0F1RwHfahVPyudj9wMS24cahQhOq9Cw";
    
    public static void main(String[] args) throws IOException {
        // 1. 初始化聊天
        JieKeAiClient chatClient = new JieKeAiClient.Builder(TOKEN)
                .botId("7482689320084734004")
                .userId("123123")
                .build();
        String chatResponse = chatClient.initializeChat();
        System.out.println("初始化聊天响应: " + chatResponse);

        // 2. 查看对话详情
        JieKeAiClient conversationClient = new JieKeAiClient.Builder(TOKEN)
                .conversationId("7482692532908965888")
                .chatId("7482695826272272420")
                .build();
        String conversationResponse = conversationClient.retrieveChat();
        System.out.println("对话详情响应: " + conversationResponse);

        // 3. 查看消息列表
        String messageListResponse = conversationClient.getMessageList();
        System.out.println("消息列表响应: " + messageListResponse);
    }
}
