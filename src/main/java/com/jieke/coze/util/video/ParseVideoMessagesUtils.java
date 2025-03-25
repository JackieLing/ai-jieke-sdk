package com.jieke.coze.util.video;

import com.google.gson.*;
import com.jieke.coze.client.JieKeAiClient;
import com.jieke.coze.model.Message;
import com.jieke.coze.model.response.ChatResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


@Slf4j
public class ParseVideoMessagesUtils {
    /** 将视频内容文案提取出来 */
    public static JsonObject getVideoToText(String token,String botId,String userId,String videoUrl) throws IOException, InterruptedException {
        // 1. 初始化聊天
        JieKeAiClient chatClient = new JieKeAiClient.Builder(token)
                .botId(botId)
                .userId(userId)
                .additionalMessage(Message.builder()
                        .contentType("text")
                        .content(videoUrl)
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
        String status = "";
        do {
            // 2. 查看对话详情
            JieKeAiClient conversationClient = new JieKeAiClient.Builder(token)
                    .conversationId(conversationId)
                    .chatId(id)
                    .build();
            String conversationResponse = conversationClient.retrieveChat();
            log.info("对话详情响应:{}",conversationResponse);

            Gson gsons = new Gson();

            JsonObject jsonObject = gsons.fromJson(conversationResponse, JsonObject.class);
            JsonObject dataObject = jsonObject.getAsJsonObject("data");
            status = dataObject.get("status").getAsString();

            log.info("智能体SDK目前从远端获取status为---->{}", status);
            log.info("智能体SDK继续轮询...");


            // 3. 查看消息列表
            String messageListResponse = conversationClient.getMessageList();
            log.info("消息列表响应:{}",messageListResponse);


            JsonObject messageListJsonObject = gsons.fromJson(messageListResponse, JsonObject.class);
            if (messageListJsonObject.has("data") && messageListJsonObject.get("data").isJsonArray()) {
                JsonArray messages = messageListJsonObject.getAsJsonArray("data");
                for (JsonElement messageElement : messages) {
                    JsonObject message = messageElement.getAsJsonObject();
                    if (message.has("content") && !message.get("content").isJsonNull()) {
                        String contentStr = message.get("content").getAsString();
                        JsonObject contentJson = gson.fromJson(contentStr, JsonObject.class);
                        if (contentJson.has("output") && !contentJson.get("output").isJsonNull() && !contentJson.get("output").getAsString().isEmpty()) {
                            status = "completed";
                            System.out.println("检测到 [ jiekeSDK ] 拿到文案值，设置 status 为 completed");
                            log.info("智能体SDK响应内容---->{}", message);
                            log.info("智能体SDK轮询OK！已完成爬虫任务！");
                            return message;
                        }
                    }
                }
            }
        } while (!status.equals("completed"));
        log.info("智能体SDK轮询结束！爬虫任务失败！");
        return null;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        JsonObject dd = ParseVideoMessagesUtils.getVideoToText(
                "pat_xVhfRxF99B1XOzqC1PTd4oZerTCb8Dn0C5QcRIT6yll5E7H44Xr23xvJR5NGUilG",
                "7485293156054024203",
                "123123",
                "https://v.douyin.com/FVeyy9kg5uA/"
        );
        System.out.println("dd"+dd);
    }
}
