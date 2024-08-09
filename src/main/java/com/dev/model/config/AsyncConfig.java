package com.dev.model.config;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
   public static ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(
           Runtime.getRuntime().availableProcessors(),
           Runtime.getRuntime().availableProcessors() * 2,
            1,
           TimeUnit.SECONDS,
           new LinkedBlockingQueue<>(),
           ThreadFactoryBuilder.create().setNamePrefix("async thread-").build(),
           new ThreadPoolExecutor.CallerRunsPolicy());
    @Override
    public Executor getAsyncExecutor() {
        return threadPoolExecutor;
    }


    @Bean(name = "email")
    public Executor canalExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("email-");
        executor.initialize();
        return executor;
    }
}
