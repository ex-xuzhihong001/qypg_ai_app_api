package com.rxy.qypg.app.api.controller;

import io.github.asleepyfish.config.ChatGPTProperties;
import io.github.asleepyfish.service.OpenAiProxyService;
import io.github.asleepyfish.util.OpenAiUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.List;


@Api(tags = "gpt问答")
@Controller
@RequestMapping(value = "/gpt/v1")
public class ChatGPTController {

    @ApiOperation(value = "普通问答")
    @GetMapping("/chat")
    public List<String> chat(String content) {
        return OpenAiUtils.createChatCompletion(content);
    }

    @ApiOperation(value = "流式问答，返回到控制台")
    @GetMapping("/streamChat")
    public void streamChat(String content) {
         OpenAiUtils.createStreamChatCompletion(content, System.out);
    }

    @ApiOperation(value = "流式问答，输出结果到WEB浏览器端")
    @GetMapping("/streamChatWithWeb")
    public void streamChatWithWeb(String content, HttpServletResponse response) throws IOException, InterruptedException {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        OpenAiUtils.createStreamChatCompletion(content, response.getOutputStream());
    }

    /**下载图片*/
    @ApiOperation(value = "下载图片")
    @GetMapping("/downloadImage")
    public void downloadImage(String prompt, HttpServletResponse response) {
        OpenAiUtils.downloadImage(prompt, response);
    }

    @ApiOperation(value = "查询可用余额")
    @GetMapping("/billingUsage")
    public void billingUsage() {
        String monthUsage = OpenAiUtils.billingUsage("2023-04-01", "2023-05-01");
        System.out.println("四月使用：" + monthUsage + "美元");
        String totalUsage = OpenAiUtils.billingUsage();
        System.out.println("一共使用：" + totalUsage + "美元");
    }

    /**自定义Token使用（解决单个SpringBoot项目中只能指定唯一的Token[sk-xxxxxxxxxxxxx]的问题，现在可以自定义ChatGPTProperties内容，添加更多的Token实例）*/
    @ApiOperation(value = "自定义Token使用")
    @GetMapping("/customToken")
    public void customToken() {
        ChatGPTProperties chatGPTProperties = new ChatGPTProperties();
        chatGPTProperties.setToken("sk-002xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        chatGPTProperties.setProxyHost("127.0.0.1");
        chatGPTProperties.setProxyPort(7890);
        OpenAiProxyService openAiProxyService = new OpenAiProxyService(chatGPTProperties, Duration.ZERO);
        // 直接使用new出来的openAiProxyService来调用方法，每个OpenAiProxyService都拥有自己的Token。
        // 这样在一个SpringBoot项目中，就可以有多个Token，可以有更多的免费额度供使用了
        openAiProxyService.createStreamChatCompletion("Java的三大特性是什么");
    }
}
