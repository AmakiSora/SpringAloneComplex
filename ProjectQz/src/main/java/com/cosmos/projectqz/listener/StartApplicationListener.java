package com.cosmos.projectqz.listener;

import com.cosmos.projectqz.job.ExampleJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 应用开启监听器
 */
@Component
public class StartApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private Scheduler scheduler;

    /**
     * 应用开启事件
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        TriggerKey triggerKey = TriggerKey.triggerKey("ExampleTrigger1");
        try {
            Trigger trigger = scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                //触发器
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(triggerKey)
                        .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ? "))
                        .build();
                //任务
                JobDetail jobDetail = JobBuilder.newJob(ExampleJob.class)
                        .withIdentity("ExampleJob1")
                        .build();
                scheduler.scheduleJob(jobDetail, trigger);
                scheduler.start();
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
