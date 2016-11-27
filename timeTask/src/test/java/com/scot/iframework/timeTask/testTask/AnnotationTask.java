package com.scot.iframework.timeTask.testTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

/**
 * 注解任务
 * Created by shengke on 2016/11/25.
 */
@Component
public class AnnotationTask {


    @Scheduled(cron="0/5 * * * * ?")
    public void task() {
        System.out.println("---------------执行Annotation task ---------------");
    }

    @Scheduled(cron="0/5 * * * * ?")
    public void task1() {
        System.out.println("---------------执行Annotation task1 ---------------");
    }
}
