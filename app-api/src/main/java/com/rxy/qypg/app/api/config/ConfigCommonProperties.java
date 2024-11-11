package com.rxy.qypg.app.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties(prefix = "rxy.common")
@Configuration
@Data
public class ConfigCommonProperties {


    private String defaultPicture;


}
