package com.scot.iframework.timeTask.proxy;

import com.scot.iframework.timeTask.po.ScheduleTask;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.lang.reflect.Method;

/**
 * Created by shengke on 2016/11/25.
 */
public class ProxyJob implements Job{

    /**
     * 任务执行对象.
     */
    public static final String JOB_TARGET = "job_target";

    /**
     * 任务触发方法.
     */
    public static final String JOB_TRIGGER = "job_trigger";

    /**
     * 任务本身对象.
     */
    public static final String JOB_OWN = "job_own";

    /**
     * 任务方法执行参数.
     */
    public static final String JOB_PARAM = "job_param";


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap map = jobExecutionContext.getTrigger().getJobDataMap();
        ScheduleTask task = (ScheduleTask)map.get(JOB_OWN);
        Object object = map.get(JOB_TARGET);
        Method method = (Method)map.get(JOB_TRIGGER);
        Object [] params = (Object [])map.get(JOB_PARAM);
        try {
            method.invoke(object, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
