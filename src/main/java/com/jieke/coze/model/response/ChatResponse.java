package com.jieke.coze.model.response;

import lombok.Data;

@Data
public class ChatResponse {
    private ChatData data;
    private int code;
    private String msg;

    @Data
    public static class ChatData {
        private String id;
        private String conversation_id;
        private String bot_id;
        private long created_at;
        private LastError last_error;
        private String status;
    }

    @Data
    public static class LastError {
        private int code;
        private String msg;
    }
}