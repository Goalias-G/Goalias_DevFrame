package com.dev.model.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedissonConfig {

    private String host;

    private int port;

    private String password;
//    @Bean
//    public RedissonClient getRedisson() {
//        Config config = new Config();
//        config.setTransportMode(TransportMode.NIO);
//        String address = new StringBuilder("redis://").append(host).append(":").append(port).toString();
//        config.useSingleServer().setAddress(address);
//        if (null != password && !"".equals(password.trim())) {
//            config.useSingleServer().setPassword(password);
//        }
//        return Redisson.create(config);
//    }
}
