package com.zhang.llm;

import java.util.List;

/**
 * @Author: ZhangJunjie
 * @Description: TODO
 * @DateTime: 2026/11/20 17:42
 **/
public interface ChatModel {
    String chat(List<Message> messages);
}
