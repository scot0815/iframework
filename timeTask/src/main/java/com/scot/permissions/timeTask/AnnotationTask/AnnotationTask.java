package com.scot.permissions.timeTask.annotationTask;

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

    @Autowired
    private SchedulerFactoryBean schedulerFactory;

    @Scheduled(cron="0/5 * * * * ?")
    public void task() {
        //System.out.println(schedulerFactory.getPhase());
        System.out.println("---------------执行Annotation task ---------------");
    }

    @Scheduled(cron="0/5 * * * * ?")
    public void task1() {
        System.out.println("---------------执行Annotation task1 ---------------");
    }
}
