package com.jieke.coze.util.web;

import okhttp3.*;
import java.io.IOException;

public class HttpUtil {
    private final OkHttpClient client;

    public HttpUtil() {
        this.client = new OkHttpClient.Builder().build();
    }

    public String post(String url, String json, String token) throws IOException {
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, json);  // 修改参数顺序
        
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + token)
                .post(body)
                .build();
        
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }
    }
}