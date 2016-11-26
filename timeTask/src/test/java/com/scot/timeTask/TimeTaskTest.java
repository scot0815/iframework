package com.scot.timeTask;

import com.scot.iframework.timeTask.po.ScheduleTask;
import com.scot.iframework.timeTask.service.ITaskService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 任务测试
 * Created by shengke on 2016/11/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-task.xml"})
@ActiveProfiles("localhost")
public class TimeTaskTest {

    @Autowired
    private ITaskService taskService;

    @Test
    @Ignore
    public void test() {
        try {
            ScheduleTask task = taskService.addScheduleTask("job1", "Task1", "exec", "0/5 * * * * ?");
           //System.out.println(JSON.toJSON(task));
            System.out.println(taskService.getAllTask());
        } catch (Exception e) {
            e.printStackTrace();
        }


        while (true) {

        }
    }
}
