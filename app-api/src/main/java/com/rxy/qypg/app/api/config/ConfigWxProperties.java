package com.rxy.qypg.app.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties(prefix = "rxy.wx")
@Configuration
@Data
public class ConfigWxProperties {
    private String appId;
    private String appSecret;
    private String wxLoginUrl;
    private String wxTokenUrl;
    private String wxGetPhoneUrl;
}
