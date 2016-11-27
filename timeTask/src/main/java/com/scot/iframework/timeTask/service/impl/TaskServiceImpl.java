package com.scot.iframework.timeTask.service.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import com.scot.iframework.timeTask.annotation.Comment;
import com.scot.iframework.timeTask.constant.CronExpression;
import com.scot.iframework.timeTask.po.ScheduleTask;
import com.scot.iframework.timeTask.proxy.ProxyJob;
import com.scot.iframework.timeTask.service.ITaskService;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;



/**
 * 任务管理，负责输出和管理日志
 *
 * Created by shengke on 2016/11/25.
 */
@Service("taskServiceBetter")
public class TaskServiceImpl implements ITaskService, ApplicationContextAware {

    /**
     * spring 任务工厂.
     */
    @Autowired
    private SchedulerFactoryBean schedulerFactory;

    /**
     * 任务表达式的描述.
     */
    private Map<String, String> cronDesc = new LinkedHashMap<String, String>();

    /**
     * 任务列表.
     */
    private static Map<String, ScheduleTask> allTask = new LinkedHashMap<String, ScheduleTask>();

    /**
     * spring 容器对象.
     */
    private static ApplicationContext app;


    /**
     * 构造方法.
     * 加载所有的定时任务配置。
     * 如需扩展任务表达式，需扩展到CronExpression中。
     */
    public TaskServiceImpl() {
        try {
            Field[] fields = CronExpression.class.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Comment.class)) {
                    Annotation a = field.getAnnotation(Comment.class);
                    if (null != a) {
                        Method m = a.getClass().getMethod("value", null);
                        String desc = m.invoke(a, null).toString();
                        cronDesc.put(field.get(CronExpression.class).toString(), desc);
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * 创建一个调度任务.
     * @param m 方法对象
     * @return  任务信息
     * @throws Exception    异常信息
     */
    private ScheduleTask createScheduleTask(Method m) throws Exception {

        ScheduleTask task = new ScheduleTask("" + System.currentTimeMillis());

        Class clazz = m.getDeclaringClass();
        //设置任务组
        task.setGroup(clazz.getName());
        if (clazz.isAnnotationPresent(Comment.class)) {
            Annotation taskAnn = clazz.getAnnotation(Comment.class);
            Method taskAnnM = taskAnn.getClass().getMethod("value", null);
            String groupDesc = taskAnnM.invoke(taskAnn, null).toString();

            task.setGroupDesc(groupDesc);
        }

        //设置触发器信息
        task.setTrigger(clazz.getName() + "." + m.getName());

        Annotation sc = m.getAnnotation(Scheduled.class);
        if (null != sc) {
            Method cronM = sc.getClass().getMethod("cron", null);
            String cron = cronM.invoke(sc, null).toString();
            task.setTriggerDesc(cronDesc.get(cron));
            task.setCron(cron);
            task.setCronDesc(cronDesc.get(cron));
        }

        //设置任务表述
        if (m.isAnnotationPresent(Comment.class)) {
            Annotation mAnn = m.getAnnotation(Comment.class);
            Method mAnnM = mAnn.getClass().getMethod("value", null);
            String desc = mAnnM.invoke(mAnn, null).toString();
            task.setDesc(desc);
        }
        return task;
    }


    /**
     * 实现ApplicationContextAware所需接口.
     * @param app   spring 容器
     * @throws BeansException   异常
     */
    @Override
    public void setApplicationContext(ApplicationContext app) throws BeansException {
        this.app = app;

        //加载所有任务到任务队列
        for (String name : app.getBeanDefinitionNames()) {
            try {
                Class<?> c = app.getBean(name).getClass();
                for (Method m : c.getMethods()) {
                    if (m.isAnnotationPresent(Scheduled.class)) {
                        ScheduleTask task = createScheduleTask(m);
                        //将任务加入队列准备启动
                        addScheduleTask(task);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 根据id获取任务.
     * @param taskId
     * @return  定时任务
     */
    @Override
    public ScheduleTask getById(String taskId) {
        return allTask.get(taskId);
    }

    /**
     * 获取全部任务.
     * @return
     */
    @Override
    public List<ScheduleTask> getAllTask() {
        if (allTask.size() == 0) {
            return new ArrayList<ScheduleTask>();
        }
        List<ScheduleTask> r = new ArrayList<ScheduleTask>();
        r.addAll(allTask.values());
        return r;
    }

    /**
     * 获取全部表达式.
     * @return
     */
    @Override
    public Map<String, String> getAllCron() {
        return cronDesc;
    }

    /**
     * 添加任务.
     * @param task
     * @return  定时任务
     * @throws Exception 处理异常
     */
    @Override
    public ScheduleTask addScheduleTask(ScheduleTask task) throws Exception {
        if (null == task.getGroup() || task.getGroup().trim().length() == 0) {
            return null;
        }

        Class<?> clazz = Class.forName(task.getGroup());
        //先从容器中获取
        Object target = null;
        try {
            target = app.getBean(clazz);
        } catch (Exception e) {
            //容器获取异常会自己创建示例，不影响其他task
        }
        //如果没有，自己创建实例
        if (target == null) {
            target = clazz.newInstance();
        }

        Method m = clazz.getMethod(task.getTrigger().replaceAll(task.getGroup() + ".", ""));
        String taskId = task.getId();
        Scheduler sched = schedulerFactory.getScheduler();
        JobDetail taskDetail = new JobDetail(taskId, task.getGroup(), ProxyJob.class);  // 任务名，任务组，任务执行类
        // 触发器
        CronTrigger trigger = new CronTrigger(taskId, task.getTrigger());   // 触发器名,触发器组
        trigger.setCronExpression(task.getCron());  // 触发器时间设定
        trigger.getJobDataMap().put(ProxyJob.JOB_TARGET, target);
        trigger.getJobDataMap().put(ProxyJob.JOB_TRIGGER, m);
        trigger.getJobDataMap().put(ProxyJob.JOB_PARAMS, new Object[]{});
        trigger.getJobDataMap().put(ProxyJob.JOB_OWN, task);
        sched.scheduleJob(taskDetail, trigger);
        // 启动
        if (!sched.isShutdown()) {
            sched.start();
        }

        if (!allTask.containsKey(taskId)) {
            allTask.put(taskId, task);
        }
        return task;
    }


    /**
     * 添加任务.
     * @param taskName  任务名称
     * @param taskClassName 任务class名称
     * @param triggerName   任务触发器名称
     * @param cron  执行表达式
     * @return  定时任务
     * @throws Exception 处理异常
     */
    @Override
    public ScheduleTask addScheduleTask(String taskName, String taskClassName, String triggerName,
                                        String cron) throws Exception {
        return addScheduleTask(taskName, null, taskClassName, null, triggerName, cron);
    }

    /**
     * 添加任务.
     * @param taskName  任务名称
     * @param taskGroupName 任务组名
     * @param taskClassName 任务class名称
     * @param triggerGroupName  触发器组名
     * @param triggerName   触发器名称
     * @param cron  执行表达式
     * @return  定时任务
     * @throws Exception 处理异常
     */
    @Override
    public ScheduleTask addScheduleTask(String taskName, String taskGroupName, String taskClassName,
                                        String triggerGroupName, String triggerName, String cron)  throws Exception {
        Class<?> clazz = Class.forName(taskClassName);
        Method m = clazz.getMethod(triggerName);
        ScheduleTask task = createScheduleTask(m);
        task.setName(taskName);
        if (null != taskGroupName) {
            task.setGroup(taskGroupName);
        }
        task.setCron(cron);
        return addScheduleTask(task);
    }

    /**
     * 修改任务触发时间.
     * @param taskId    任务ID
     * @param cron  执行表达式
     * @return  定时任务
     */
    @Override
    public ScheduleTask modifyTaskCron(String taskId, String cron) {

        ScheduleTask task = allTask.get(taskId);
        try {

            Scheduler sched = schedulerFactory.getScheduler();
            CronTrigger trigger = (CronTrigger) sched.getTrigger(taskId, task.getTrigger());
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cron)) {
                JobDetail taskDetail = sched.getJobDetail(taskId, task.getGroup());
                Class objJobClass = taskDetail.getJobClass();

                removeTask(taskId);

                //重新生成ID
                task.setId("" + System.currentTimeMillis());
                task.setCron(cron);
                addScheduleTask(task);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return task;

    }


    /**
     * 移除任务.
     * @param taskId    任务ID
     * @return  定时任务
     */
    @Override
    public ScheduleTask removeTask(String taskId) {

        ScheduleTask task = allTask.get(taskId);
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            sched.pauseTrigger(taskId, task.getTrigger());  // 停止触发器
            sched.unscheduleJob(taskId, task.getGroup());   // 移除触发器
            sched.deleteJob(taskId, task.getGroup());   // 删除任务

            allTask.remove(taskId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return task;

    }


    /**
     * 暂停任务.
     * @param taskId    任务ID
     * @return  定时任务
     */
    @Override
    public ScheduleTask pauseTask(String taskId) {
        ScheduleTask task = allTask.get(taskId);
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            sched.pauseTrigger(task.getId(), task.getTrigger());    // 停止触发器

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return task;
    }

    /**
     * 执行一次.
     * @param taskId    任务ID
     * @return  定时任务
     */
    @Override
    public  ScheduleTask execOnce(String taskId) {
        ScheduleTask task = allTask.get(taskId);
        try {
            Scheduler sched = schedulerFactory.getScheduler();

            //组装执行数据
            JobDataMap map = new JobDataMap();
            Class<?> clazz = Class.forName(task.getGroup());
            Object target = null;
            try {
                target = app.getBean(clazz);
            } catch (Exception e) {
                //容器获取异常会自己创建示例，不影响其他task
            }
            if (target == null) {
                target = clazz.newInstance();
            }
            Method m = clazz.getMethod(task.getTrigger().replaceAll(task.getGroup() + ".", ""));

            // 触发器
            map.put(ProxyJob.JOB_TARGET, target);
            map.put(ProxyJob.JOB_TRIGGER, m);
            map.put(ProxyJob.JOB_PARAMS, new Object[]{});
            map.put(ProxyJob.JOB_OWN, task);

            sched.triggerJob(task.getId(), task.getGroup(), map);   // 执行一次

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return task;
    }

    /**
     * 重启任务.
     * @param taskId    任务ID
     * @return  定时任务
     */
    @Override
    public ScheduleTask restartTask(String taskId) {
        ScheduleTask task = allTask.get(taskId);
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            // 重启触发器
            sched.resumeTrigger(task.getId(), task.getTrigger());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return task;
    }


    /**
     * 禁用定时任务.
     * @param taskId    任务ID
     * @return  定时任务
     */
    @Override
    public ScheduleTask disableTask(String taskId) {
        ScheduleTask task = allTask.get(taskId);
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            sched.pauseTrigger(taskId, task.getTrigger());  // 停止触发器
            sched.unscheduleJob(taskId, task.getGroup());   // 移除触发器
            sched.deleteJob(taskId, task.getGroup());   // 删除任务

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return task;
    }


    /**
     * 启动所有定时任务.
     * 仅仅启动任务调度控制，之前暂停任务不启动.
     */
    @Override
    public void startAllTask() {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            sched.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭所有定时任务.
     * 任务调度控制器关闭
     * 不能进行其他操作，包括重启.
     */
    @Override
    public void shutDownAllTask() {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
