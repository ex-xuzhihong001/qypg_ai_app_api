package com.rxy.qypg.app.api;

import io.github.asleepyfish.util.OpenAiUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class ChatGPTTest {
    @Test
    public void testChatGPT() {
        List<String> completion = OpenAiUtils.createCompletion("世界上最高的山峰是什么？");
        completion.forEach(System.out::println);
    }

    @Test
    public void testGenerateImg() {
        OpenAiUtils.createImage("英短").forEach(System.out::println);
    }
}
