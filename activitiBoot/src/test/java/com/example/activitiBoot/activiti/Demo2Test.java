package com.example.activitiBoot.activiti;

import com.example.activitiBoot.ActivitiBootApplicationTests;
import com.google.common.collect.Maps;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.activiti.bpmn.model.Process;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: kangerbao
 * @createTime: 2022年04月21日 14:12:13
 * @version: 1.0
 * @Description:
 */
public class Demo2Test extends ActivitiBootApplicationTests {

    @Resource
    private RepositoryService repositoryService;

    @Resource
    TaskService taskService;

    @Resource
    RuntimeService runtimeService;

    @Test
    public void deploymentTest(){
// 创建开始
        StartEvent startEvent = new StartEvent();
        startEvent.setId("start");

        UserTask fristUserTask = new UserTask();
        fristUserTask.setId("frist");
        fristUserTask.setAssignee("${fristUser}");

        UserTask SecondUserTask = new UserTask();
        SecondUserTask.setId("second");
        SecondUserTask.setAssignee("${secondUser}");

        EndEvent endEvent = new EndEvent();
        endEvent.setId("end");


        // 创建连线
        SequenceFlow s1 = new SequenceFlow();
        s1.setId("s1");
        s1.setSourceRef("start");
        s1.setTargetRef("frist");

        SequenceFlow s2 = new SequenceFlow();
        s2.setId("s2");
        s2.setSourceRef("frist");
        s2.setTargetRef("second");


        SequenceFlow s3 = new SequenceFlow();
        s3.setId("s3");
        s3.setSourceRef("second");
        s3.setTargetRef("end");


        // 创建流程
        Process process = new Process();
        process.setName("AssigneeVar");
        //重要 processKey
        process.setId("AssigneeVar");
        process.addFlowElement(startEvent);
        process.addFlowElement(fristUserTask);
        process.addFlowElement(SecondUserTask);
        process.addFlowElement(endEvent);
        process.addFlowElement(s1);
        process.addFlowElement(s2);
        process.addFlowElement(s3);

        // 创建Bpmnmodel
        BpmnModel bpmnModel = new BpmnModel();
        bpmnModel.addProcess(process);

        Deployment deployment = repositoryService.createDeployment()
                .name(process.getName() + "bpmn")
                .addBpmnModel(process.getName() + "bpmnModel.bpmn", bpmnModel) // 这个addBpmnModel第一个参数一定要带后缀.bpmn
                .key("AssigneeVarKey")//重要deploymentKey
                .tenantId("kangerbaoTest")
                .deploy();

        System.out.println("流程部署id：" + deployment.getId());
        System.out.println("流程部署名称：" + deployment.getName());
    }

    @Test
    public void querydeployment(){
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentKey("AssigneeVarKey").singleResult();
        System.out.println("流程部署id：" + deployment.getId());
        System.out.println("流程部署名称：" + deployment.getName());
    }

    @Test
    public void testStartProcess(){
        Map<String, Object> variables = Maps.newHashMap();
        variables.put("fristUser","tom");
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentKey("AssigneeVarKey").singleResult();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceById(processDefinition.getId(),"buskey",variables);
        System.out.println("流程定义id：" + processInstance.getProcessDefinitionId());
        System.out.println("流程实例id：" + processInstance.getId());
        System.out.println("当前活动Id：" + processInstance.getActivityId());
    }

    @Test
    public void querytask(){
        //任务负责人
        String assignee = "tom";
        List<Task> list = taskService.createTaskQuery().taskAssignee(assignee).list();
        for (Task task : list) {
            System.out.println("流程实例id：" + task.getProcessInstanceId());
            System.out.println("任务id：" + task.getId());
            System.out.println("任务负责人：" + task.getAssignee());
            System.out.println("任务名称：" + task.getName());
            //taskService.complete(task.getId());
        }
    }

    @Test
    public void completetask(){
        Map<String, Object> variables = Maps.newHashMap();
        variables.put("secondUser","jeefy");
        //任务负责人
        String assignee = "tom";
        List<Task> list = taskService.createTaskQuery().taskAssignee(assignee).list();
        for (Task task : list) {
            taskService.complete(task.getId(),variables);
        }
    }

    @Test
    public void querytask2(){
        //任务负责人
        String assignee = "jeefy";
        List<Task> list = taskService.createTaskQuery().taskAssignee(assignee).list();
        for (Task task : list) {
            System.out.println("流程实例id：" + task.getProcessInstanceId());
            System.out.println("任务id：" + task.getId());
            System.out.println("任务负责人：" + task.getAssignee());
            System.out.println("任务名称：" + task.getName());
            //taskService.complete(task.getId());
        }
    }

    @Test
    public void completetask2(){
        Map<String, Object> variables = Maps.newHashMap();
        variables.put("secondUser","dauk");
        //任务负责人
        String assignee = "jeefy";
        List<Task> list = taskService.createTaskQuery().taskAssignee(assignee).list();
        for (Task task : list) {
            taskService.complete(task.getId(),variables);
        }
    }

}
