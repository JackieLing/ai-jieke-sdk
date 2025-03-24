package com.jieke.coze.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
    private String contentType;
    private String content;
    private String role;
    private String type;
}