package com.heyi.webapp.filesystem.job;

import com.heyi.core.filestorage.service.BcTempFileTransactionService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Component
@EnableScheduling
public class TransactionPackJob {

    @Autowired
    BcTempFileTransactionService service;

    protected void execute() {
        service.packTransactions();
    }

    @Configuration
    static class TransactionPackJobConfig {

        // 配置定时任务
        @Bean(name = "transactionPackJobDetail")
        public MethodInvokingJobDetailFactoryBean transactionPackJobDetail(TransactionPackJob transactionPackJob) {
            MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
            // 是否并发执行
            jobDetail.setConcurrent(false);
            // 为需要执行的实体类对应的对象
            jobDetail.setTargetObject(transactionPackJob);
            // 需要执行的方法
            jobDetail.setTargetMethod("execute");
            return jobDetail;
        }

        // 配置触发器
        @Bean(name = "transactionPackJobTrigger")
        public SimpleTriggerFactoryBean transactionPackJobTrigger(JobDetail transactionPackJobDetail) {
            SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
            trigger.setJobDetail(transactionPackJobDetail);
            // 设置任务启动延迟
            trigger.setStartDelay(0);
            // 每5秒执行一次
            trigger.setRepeatInterval(8000);
            return trigger;
        }

    }

}




