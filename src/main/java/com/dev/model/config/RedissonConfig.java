package com.dev.model.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 王智勇
 * @Date 2022/7/28 15:25
 * @PackageName:com.vpu.mp.config
 * @ClassName: RedissionConfig
 * @Description: redis分布式锁config
 * @Version 1.0
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host:127.0.0.1}")
    private String host;
    @Value("${spring.redis.port:6379}")
    private int port;
    @Value("${spring.redis.password:#{null}}")
    private String password;
    @Bean
    public RedissonClient getRedisSon() {
        Config config = new Config();
        config.setTransportMode(TransportMode.NIO);
        String address = new StringBuilder("redis://").append(host).append(":").append(port).toString();
        config.useSingleServer().setAddress(address);
        if (null != password && !"".equals(password.trim())) {
            config.useSingleServer().setPassword(password);
        }
        return Redisson.create(config);
    }
}
