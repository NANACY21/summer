package com.matchacloud.summerstarter.student.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    // 获取当前类对应的日志记录器
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    /**
     * 每 5 秒执行一次
     */
    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        // 记录不同级别的日志
        logger.trace("进入 reportCurrentTime 方法，开始执行任务");
        logger.debug("正在执行任务的某个步骤");
        logger.info("任务执行完成，一切正常");
        System.out.println("当前时间：" + new java.util.Date());
    }
}
