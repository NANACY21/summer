package com.matchacloud.summerstarter.student.service;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * XXL-JOB 是一个分布式任务调度平台，其功能包括定时任务
 *
 * 定时任务：
 * 每天12点执行 或 每10分钟执行一次
 *
 *
 */
@Component
@EnableScheduling
public class ScheduledTasks {
    /**
     * 每 5 秒执行一次
     */
    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        System.out.println("当前时间：" + new java.util.Date());
    }
}
