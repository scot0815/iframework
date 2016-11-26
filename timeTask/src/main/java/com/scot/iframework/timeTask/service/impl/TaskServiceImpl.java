package com.scot.iframework.timeTask.service.impl;

import com.scot.iframework.timeTask.po.ScheduleTask;
import com.scot.iframework.timeTask.proxy.ProxyJob;
import com.scot.iframework.timeTask.service.ITaskService;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时任务实现
 * Created by shengke on 2016/11/25.
 */
@Service("taskService")
public class TaskServiceImpl implements ITaskService, ApplicationContextAware{

    /**
     * 全部表达式.
     */
    private Map<String, String> allCron = new HashMap<String, String>();

    /**
     * 全部任务.
     */
    private List<ScheduleTask> allTask = new ArrayList<ScheduleTask>();

    /**
     * 任务工厂类.
     */
    @Autowired
    private SchedulerFactoryBean schedulerFactory;

    /**
     * spring 容器.
     */
    private ApplicationContext applicationContext;

    /**
     * 获取全部表达式.
     *
     * @return
     */
    @Override
    public Map<String, String> getAllCron() {
        return allCron;
    }

    /**
     * 获取全部任务.
     *
     * @return
     */
    @Override
    public List<ScheduleTask> getAllTask() {
        return allTask;
    }

    /**
     * 根据id获取任务.
     *
     * @param id
     * @return 定时任务
     */
    @Override
    public ScheduleTask getById(String id) {
        return null;
    }

    /**
     * 构建任务.
     * @param method
     * @return
     */
    private ScheduleTask createScheduleTask(Method method) throws Exception {
        ScheduleTask task = new ScheduleTask();
        task.setId(""+System.currentTimeMillis());
        Class<?> clazz = method.getDeclaringClass();
        task.setGroup(clazz.getName());
        task.setTrigger(method.getName());

        Annotation an = method.getAnnotation(Scheduled.class);
        if(null != an) {
            Method cm = an.getClass().getMethod("cron");
            String cron = cm.invoke(an).toString();
            task.setCron(cron);
        }
        return task;
    }

    /**
     * 添加任务.
     *
     * @param task
     * @return 定时任务
     */
    @Override
    public ScheduleTask addScheduleTask(ScheduleTask task) throws Exception {

        Scheduler scheduler = schedulerFactory.getScheduler();
        JobDetail jobDetail = new JobDetail(task.getId(), task.getGroup(), ProxyJob.class);
        CronTrigger trigger = new CronTrigger(task.getId(), task.getGroup(), task.getCron());

        Class<?> clazz = Class.forName(task.getGroup());
        Object object = clazz.newInstance();
        Method method = clazz.getMethod(task.getTrigger());
        trigger.getJobDataMap().put(ProxyJob.JOB_TARGET, object);
        trigger.getJobDataMap().put(ProxyJob.JOB_TRIGGER, method);
        trigger.getJobDataMap().put(ProxyJob.JOB_OWN, task);
        trigger.getJobDataMap().put(ProxyJob.JOB_PARAM, new Object[]{});
        scheduler.scheduleJob(jobDetail,trigger);

        if(!scheduler.isShutdown()) {
            scheduler.start();
        }

        if(!allCron.containsKey(task.getId())) {
            allCron.put(task.getId(), task.getCron());
            allTask.add(task);
        }
        return task;
    }

    /**
     * 添加任务.
     *
     * @param taskName      任务名称
     * @param taskClassName 任务class名称
     * @param triggerName   任务触发器名称
     * @param cron          执行表达式
     * @return 定时任务
     */
    @Override
    public ScheduleTask addScheduleTask(String taskName, String taskClassName, String triggerName, String cron) throws Exception {
        Class<?> clazz = Class.forName(taskClassName);
        Method method = clazz.getMethod(triggerName);
        ScheduleTask task = createScheduleTask(method);
        task.setCron(cron);
        task.setName(taskName);
        return addScheduleTask(task);
    }

    /**
     * 添加任务.
     *
     * @param taskName         任务名称
     * @param taskGroupName    任务组名
     * @param taskClassName    任务class名称
     * @param triggerGroupName 触发器组名
     * @param triggerName      触发器名称
     * @param cron             执行表达式
     * @return 定时任务
     */
    @Override
    public ScheduleTask addScheduleTask(String taskName, String taskGroupName, String taskClassName, String triggerGroupName, String triggerName, String cron) {
        return null;
    }

    /**
     * 修改任务触发时间.
     *
     * @param taskId 任务ID
     * @param cron   执行表达式
     * @return
     */
    @Override
    public ScheduleTask modifyTaskCron(String taskId, String cron) {
        return null;
    }

    /**
     * 移除任务.
     *
     * @param taskId
     * @return
     */
    @Override
    public ScheduleTask removeTask(String taskId) {
        return null;
    }

    /**
     * 重启任务.
     *
     * @param taskId
     * @return
     */
    @Override
    public ScheduleTask restartTask(String taskId) {
        return null;
    }

    /**
     * 暂停任务.
     *
     * @param taskId
     * @return
     */
    @Override
    public ScheduleTask pauseTask(String taskId) {
        return null;
    }

    /**
     * 禁用定时任务.
     *
     * @param taskId
     * @return
     */
    @Override
    public ScheduleTask disableTask(String taskId) {
        return null;
    }

    /**
     * 关闭定时任务.
     *
     * @param taskId
     * @return
     */
    @Override
    public ScheduleTask shutdownTask(String taskId) {
        return null;
    }

    /**
     * 启动所有定时任务.
     */
    @Override
    public void startAllTask() {

    }

    /**
     * 关闭所有定时任务.
     */
    @Override
    public void shutDownAllTask() {

    }

    /**
     * 获取注解任务
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        for (String name : applicationContext.getBeanDefinitionNames()) {
            Class<?> clazz = applicationContext.getBean(name).getClass();
            for (Method m : clazz.getDeclaredMethods()) {
                if(m.isAnnotationPresent(Scheduled.class)) {
                    try {
                        ScheduleTask task = createScheduleTask(m);
                        addScheduleTask(task);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
