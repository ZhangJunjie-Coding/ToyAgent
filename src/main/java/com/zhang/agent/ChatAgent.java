package com.zhang.agent;

import com.zhang.llm.ChatModel;
import com.zhang.llm.Message;

import java.util.List;

/**
 * @Author: ZhangJunjie
 * @Description: TODO
 * @DateTime: 2026/11/20 19:43
 **/
public interface ChatAgent extends Agent {
    class Impl implements ChatAgent {

        private final ChatModel model;

        public Impl(ChatModel model) {
            this.model = model;
        }

        @Override
        public String name() {
            return "Stem01 - 对话智能体(最小MVP)";
        }

        @Override
        public String chat(String input) {
            return model.chat(List.of(Message.user(input)));
        }

    }

    public static void main(String[] args) {

    }
}
