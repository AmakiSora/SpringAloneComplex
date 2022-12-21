package com.cosmos.projectqz.config;

import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.concurrent.Executor;

/**
 * 调度器配置
 */
@Configuration
public class SchedulerConfig {
    /**
     * 调度器
     * (一般情况下,只有一个)
     */
    @Bean
    public Scheduler scheduler() {
        return schedulerFactoryBean().getScheduler();
    }

    /**
     * 调度器工厂bean
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        //设置调度器名(可忽略)
        schedulerFactory.setSchedulerName("ExampleScheduler");
        //设置应用调度器key(可忽略)
        schedulerFactory.setApplicationContextSchedulerContextKey("appExampleScheduler");
        //设置线程池
        schedulerFactory.setTaskExecutor(schedulerThreadPool());
        return schedulerFactory;
    }

    /**
     * 调度器线程池
     */
    @Bean
    public Executor schedulerThreadPool(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //设置线程池的基本大小(Java虚拟机可用的处理器数)
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        //设置线程池中允许的最大线程数
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors());
        //设置队列容量
        executor.setQueueCapacity(Runtime.getRuntime().availableProcessors());
        return executor;
    }
}
