package com.zhang.llm;

/**
 * @Author: ZhangJunjie
 * @Description: TODO
 * @DateTime: 2026/11/20 17:44
 **/
public record Message(String role, String content) {

    public static Message system(String content) {
        return new Message("system", content);
    }

    public static Message user(String content) {
        return new Message("user", content);
    }

    public static Message assistant(String content) {
        return new Message("assistant", content);
    }
}
