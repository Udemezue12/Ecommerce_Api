package com.uchechukwu.store.configProperties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedisConfigurationProperties {

    private String url;
    private String host;

    private String username;

    private String password;

    private int port;
}
