package com.scot.iframework.timeTask;

import com.scot.iframework.timeTask.constant.CronExpression;
import com.scot.iframework.timeTask.po.ScheduleTask;
import com.scot.iframework.timeTask.service.ITaskService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


/**
 * 任务测试
 * Created by shengke on 2016/11/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-task.xml"})
@ActiveProfiles("localhost")
public class TimeTaskTest {

    @Resource(name = "taskServiceBetter")
    private ITaskService taskService;

    @Test
    @Ignore
    public void test() {
        try {
            ScheduleTask task = taskService.addScheduleTask("job1", "com.scot.iframework.timeTask.testTask.Task1", "exec", "0/5 * * * * ?");
           //System.out.println(JSON.toJSON(task));
            System.out.println(taskService.getAllTask());


            String taskId = taskService.getAllTask().get(0).getId();
            System.out.println(taskService.getAllTask());;
            Thread.sleep(10000);
            System.out.println("----------修改表达式-------------");
            taskService.modifyTaskCron(taskId, CronExpression.EVERY_2_SECOND);

            Thread.sleep(10000);
            System.out.println("----------暂停任务-------------");
            taskId = taskService.getAllTask().get(0).getId();
            taskService.pauseTask(taskId);

            Thread.sleep(10000);
            System.out.println("----------重启任务-------------");
            taskService.restartTask(taskId);

            System.out.println("----------添加任务-------------");
            ScheduleTask task2 = taskService.addScheduleTask("job2", "com.scot.iframework.timeTask.testTask.Task1", "exec2", "0/2 * * * * ?");


            Thread.sleep(10000);
            System.out.println("----------暂停任务-------------");
            taskId = taskService.getAllTask().get(0).getId();
            taskService.pauseTask(taskId);

            Thread.sleep(10000);
            System.out.println("----------关闭所有任务-------------");
            taskService.shutDownAllTask();


        } catch (Exception e) {
            e.printStackTrace();
        }


        while (true) {

        }
    }

    @Test
    @Ignore
    public void test1() {
        try {
            ScheduleTask task = taskService.addScheduleTask("job1", "com.scot.iframework.timeTask.testTask.Task1", "exec", "0 0/1 * * * ?");
            Thread.sleep(10000);
            taskService.execOnce(task.getId());
            while (true) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
