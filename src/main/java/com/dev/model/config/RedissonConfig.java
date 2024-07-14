package com.dev.model.config;


import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: RedissionConfig
 * @Description: redis分布式锁config
 * @Version 1.0
 */
@Configuration
public class RedissonConfig {

/*    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
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
    }*/
}
