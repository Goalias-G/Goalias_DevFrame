package com.aigc.vrmetal.config;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

@Configuration
public class AsyncConfig implements AsyncConfigurer {
   public static ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors() * 2,
            1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), ThreadFactoryBuilder.create().setNamePrefix("async thread-").build(),new ThreadPoolExecutor.AbortPolicy());
    @Override
    public Executor getAsyncExecutor() {
        return threadPoolExecutor;
    }

    @Bean(name = "taskExecutor1")//可以用async的value属性指定
    public Executor taskExecutor1() {
        return Executors.newFixedThreadPool(10);
    }

    @Bean(name = "taskExecutor2")
    public Executor taskExecutor2() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("MyExecutor-");
        executor.initialize();
        return executor;
    }
}
