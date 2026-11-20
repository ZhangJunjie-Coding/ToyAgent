package com.zhang.agent;

import java.util.List;
import java.util.Map;

/**
 * @Author: ZhangJunjie
 * @Description: TODO
 * @DateTime: 2026/11/20 19:41
 **/
public interface Agent {
    String name();

    String chat(String input);

    default List<Map<String, Object>> trace() {
        return List.of();
    }

    default void reset() {

    }
}
