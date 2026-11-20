package com.zhang.llm;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ZhangJunjie
 * @Description: TODO
 * @DateTime: 2026/11/20 17:46
 **/
public final class OpenAiChatModel implements ChatModel {


    private final String baseUrl;
    private final String apiKey;
    private final String model;

    private final HttpClient http = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(15)).build();

    public OpenAiChatModel(String baseUrl, String apiKey, String model) {
        this.baseUrl = baseUrl.replaceAll("/*$", "");
        this.apiKey = apiKey;
        this.model = model;
    }


    @Override
    public String chat(List<Message> messages) {
        LinkedHashMap<Object, Object> body = new LinkedHashMap<>();
        body.put("model", model);
        body.put("messages", messages.stream()
                .map(m -> Map.of("role", m.role(), "content", m.content()))
                .toList());

//        body.put("thinking",Map.of("type","enabled"));
//        body.put("reasoning_effort","high");
//        body.put("stream",false);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .timeout(Duration.ofSeconds(120))
                .POST(HttpRequest.BodyPublishers.ofString(Json.write(body)))
                .build();

        body.put("temperature", 0.7);

        try {
            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IllegalStateException("模型调用失败 HTTP " + response.statusCode() + ": " + response.body());
            }

            Object root = Json.parse(response.body());
            Object choices = ((Map<?, ?>) root).get("choices");
            Object first = ((List<?>) choices).get(0);
            Object message = ((Map<?, ?>) first).get("message");

            return String.valueOf(((Map<?, ?>) message).get("content"));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
