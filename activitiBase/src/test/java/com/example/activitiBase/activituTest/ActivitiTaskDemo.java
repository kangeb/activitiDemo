package com.example.activitiBase.activituTest;

import com.example.activitiBase.ActivitiBaseApplicationTests;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.List;

public class ActivitiTaskDemo extends ActivitiBaseApplicationTests {

    @Resource
    TaskService taskService;

    @Test
    public void testFindPersonalTaskList() {
//        任务负责人
        String assignee = "zhan";
//        根据流程key 和 任务负责人 查询任务
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey("myProcess_1") //流程Key
                .taskAssignee(assignee)//只查询该任务负责人的任务
                .list();

        for (Task task : list) {

            System.out.println("流程实例id：" + task.getProcessInstanceId());
            System.out.println("任务id：" + task.getId());
            System.out.println("任务负责人：" + task.getAssignee());
            System.out.println("任务名称：" + task.getName());

        }
    }

    @Test
    public void complete() {
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("myProcess_1") //流程Key
                .taskAssignee("zhan")  //要查询的负责人
                .singleResult();
//        完成任务,参数：任务id
        taskService.complete(task.getId());
    }

    @Test
    public void complete2() {
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("myProcess_1") //流程Key
                .taskAssignee("wang")  //要查询的负责人
                .singleResult();
//        完成任务,参数：任务id
        taskService.complete(task.getId());
    }

    @Test
    public void complete3() {
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("myProcess_1") //流程Key
                .taskAssignee("li")  //要查询的负责人
                .singleResult();
//        完成任务,参数：任务id
        taskService.complete(task.getId());
    }

    @Test
    public void complete5() {
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("myProcess_1") //流程Key
                .taskAssignee("zhao")  //要查询的负责人
                .singleResult();
//        完成任务,参数：任务id
        taskService.complete(task.getId());
    }
}
