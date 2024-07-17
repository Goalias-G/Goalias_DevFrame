package com.dev.model.config;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Data
@Configuration
@ConfigurationProperties(prefix = "goalias.canal")
public class CanalConfig {

    private String hostname;

    private Integer port;

    private String destination;

    private String username="";

    private String password="";

    private String schema = ".*";

    private String userTable = "..*";

    private boolean insert = false;

    private boolean delete = false;

    private boolean update = false;

    private boolean select = false;

    private boolean create = false;
    /**
     * 订阅规则
     * 1.  所有表：.*   or  .*\\..*
     * 2.  canal schema下所有表： canal\\..*
     * 3.  canal下的以canal打头的表：canal\\.canal.*
     * 4.  canal schema下的一张表：canal\\.test1
     * 5.  多个规则组合使用：canal\\..*,mysql.test1,mysql.test2 (逗号分隔)
     */

    @Bean
    public CanalConnector createCanalConnector() {
        return CanalConnectors.newSingleConnector(new InetSocketAddress(port), destination, username, password);
    }
}
