package com.rxy.qypg.app.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "rxy.oss")
@Configuration
@Data
public class ConfigOssProperties {
    private String accessKeyId;
    private String accessKeySecret;
    private String endpoint;
    private String roleArn;
    private String ossEndpoint;
    private String bucketName;
    private String domain;
}