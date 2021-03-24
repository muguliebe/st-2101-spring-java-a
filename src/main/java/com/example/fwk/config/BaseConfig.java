package com.example.fwk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
public class BaseConfig {

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10000);
        executor.setMaxPoolSize(10000);
        executor.setQueueCapacity(30000);
        executor.setThreadNamePrefix("thread-");
        executor.initialize();
        return executor;
    }

}
