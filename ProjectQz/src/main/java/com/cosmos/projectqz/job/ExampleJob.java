package com.cosmos.projectqz.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 任务
 */
@DisallowConcurrentExecution//保证该任务执行完成之后才能继续
public class ExampleJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            System.out.println("---------------------------------------------");
            //获取调度器信息
            System.out.println("调度器的id:" + context.getScheduler().getSchedulerInstanceId());
            System.out.println("调度器的名称:" + context.getScheduler().getSchedulerName());
            //获取触发器实例的名称
            System.out.println("触发器实例的名称:" + context.getTrigger().getKey().getName());
            System.out.println("触发器实例所在组的名称:" + context.getTrigger().getKey().getGroup());
            //获取任务实例的名称
            System.out.println("任务实例的名称" + context.getJobDetail().getKey().getName());
            System.out.println("任务实例所在组的名称" + context.getJobDetail().getKey().getGroup());
            //任务执行时间
            System.out.println("任务执行时间:" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            System.out.println("---------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
