package com.example.activitiBase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @Author: kangerbao
 * @createTime: 2022年04月21日 14:08:50
 * @version: 1.0
 * @Description:
 */
@Configuration

public class ThreadPoolTaskExecutorConfig {

    private final int corePoolSize = 10;
    private final int maxPoolSize = 50;
    private final int queueCapacity = 10;

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.initialize();
        return executor;
    }
}
