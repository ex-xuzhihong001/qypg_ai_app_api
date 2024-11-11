package com.rxy.qypg.app.api;

import io.github.asleepyfish.annotation.EnableChatGPT;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableScheduling
@EnableSwagger2
@EnableChatGPT
@SpringBootApplication(scanBasePackages = {"com.rxy.qypg.common","com.rxy.qypg.app.api"})
public class AppApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(com.rxy.qypg.app.api.AppApiApplication.class, args);
    }
}
