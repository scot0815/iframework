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
public class ProxyJob implements Job {

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
    public static final String JOB_PARAMS = "job_param";

    /**
     * 执行时间.
     */
    private ThreadLocal<Entry> local = new ThreadLocal<Entry>();


    /**
     * 定时任务实际执行入口.
     * @param jobExecutionContext   job执行上下文
     * @throws JobExecutionException    异常
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        local.set(new Entry());

        JobDataMap map = jobExecutionContext.getTrigger().getJobDataMap();
        ScheduleTask task = (ScheduleTask) map.get(JOB_OWN);
        Object object = map.get(JOB_TARGET);
        Method method = (Method) map.get(JOB_TRIGGER);
        Object[] params = (Object[]) map.get(JOB_PARAMS);
        try {
            //修改任务执行次数
            task.setExecute(task.getExecute() + 1);
            local.get().start = System.currentTimeMillis();

            //调用触发器
            method.invoke(object, params);

            local.get().end = System.currentTimeMillis();
            task.setLastTime(local.get().start);
            task.setLastFinishTime(local.get().end);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class Entry {
        public long start = 0L;
        public long end = 0L;
    }
}
