package com.jieke.coze.model.request;

public class ChatInitRequest {
    private String bot_id;
    private String user_id;

    public ChatInitRequest(String botId, String userId) {
        this.bot_id = botId;
        this.user_id = userId;
    }

    // Getters and Setters
    public String getBot_id() {
        return bot_id;
    }

    public void setBot_id(String bot_id) {
        this.bot_id = bot_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}