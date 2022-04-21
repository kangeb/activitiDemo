package com.example.activitiBoot.activiti;

import com.example.activitiBoot.ActivitiBootApplicationTests;
import com.google.common.collect.Lists;
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

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: kangerbao
 * @createTime: 2022年04月21日 14:12:13
 * @version: 1.0
 * @Description:
 */
public class DemoTest extends ActivitiBootApplicationTests {

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
        //创建排他网关
        ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
        exclusiveGateway.setId("eg1");
        // 创建用户任务
        //张三审批金额小于500
        UserTask zhangsanUserTask = new UserTask();
        zhangsanUserTask.setId("u_zhangsan");
        zhangsanUserTask.setAssignee("zhangsan");
        //zhangsanUserTask.setCandidateUsers(Lists.newArrayList("zhangyi","zhanger"));
        //李四审批金额大于500
        UserTask lisiUserTask = new UserTask();
        lisiUserTask.setId("u_lisi");
        lisiUserTask.setAssignee("lisi");
        //王五最后审批
        UserTask wangwuUserTask = new UserTask();
        wangwuUserTask.setId("u_wangwu");
        wangwuUserTask.setAssignee("wangwu");
        // 创建结束点
        EndEvent endEvent = new EndEvent();
        endEvent.setId("end");


        // 创建连线
        SequenceFlow s1 = new SequenceFlow();
        s1.setId("s1");
        s1.setSourceRef("start");
        s1.setTargetRef("eg1");

        SequenceFlow s2 = new SequenceFlow();
        s2.setId("s2");
        s2.setSourceRef("eg1");
        s2.setTargetRef("u_zhangsan");
        //格式 ${submitType == "y"|| submitType == "Y"}
        s2.setConditionExpression("${money < 500}");

        SequenceFlow s3 = new SequenceFlow();
        s3.setId("s3");
        s3.setSourceRef("eg1");
        s3.setTargetRef("u_lisi");
        //格式 ${submitType == "y"|| submitType == "Y"}
        s3.setConditionExpression("${money >= 500}");

        SequenceFlow s4 = new SequenceFlow();
        s4.setId("s4");
        s4.setSourceRef("u_zhangsan");
        s4.setTargetRef("u_wangwu");
        //格式 ${submitType == "y"|| submitType == "Y"}
        s4.setConditionExpression("${money < 500}");

        SequenceFlow s5 = new SequenceFlow();
        s5.setId("s5");
        s5.setSourceRef("u_lisi");
        s5.setTargetRef("u_wangwu");

        SequenceFlow s6 = new SequenceFlow();
        s6.setId("s6");
        s6.setSourceRef("u_wangwu");
        s6.setTargetRef("end");



        // 创建流程
        org.activiti.bpmn.model.Process process = new org.activiti.bpmn.model.Process();
        process.setName("lastCandi");
        //重要 processKey
        process.setId("lastCandi");
        process.addFlowElement(startEvent);
        process.addFlowElement(exclusiveGateway);
        process.addFlowElement(zhangsanUserTask);
        process.addFlowElement(lisiUserTask);
        process.addFlowElement(wangwuUserTask);
        process.addFlowElement(endEvent);
        process.addFlowElement(s1);
        process.addFlowElement(s2);
        process.addFlowElement(s3);
        process.addFlowElement(s4);
        process.addFlowElement(s5);
        process.addFlowElement(s6);

        // 创建Bpmnmodel
        BpmnModel bpmnModel = new BpmnModel();
        bpmnModel.addProcess(process);

        Deployment deployment = repositoryService.createDeployment()
                .name(process.getName() + "bpmn")
                .addBpmnModel(process.getName() + "bpmnModel.bpmn", bpmnModel) // 这个addBpmnModel第一个参数一定要带后缀.bpmn
                .key("CandiKey")//重要deploymentKey
                .tenantId("kangerbaoTest")
                .deploy();

        System.out.println("流程部署id：" + deployment.getId());
        System.out.println("流程部署名称：" + deployment.getName());
    }

    @Test
    public void querydeployment(){
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentKey("CandiKey").singleResult();
        System.out.println("流程部署id：" + deployment.getId());
        System.out.println("流程部署名称：" + deployment.getName());
    }

    @Test
    public void testStartProcess(){
        Map<String, Object> variables = Maps.newHashMap();
        variables.put("money",200);
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentKey("CandiKey").singleResult();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceById(processDefinition.getId(),"lastCandibuskey",variables);
        System.out.println("流程定义id：" + processInstance.getProcessDefinitionId());
        System.out.println("流程实例id：" + processInstance.getId());
        System.out.println("当前活动Id：" + processInstance.getActivityId());
    }

    @Test
    public void querytask(){
        //任务负责人
        String assignee = "zhangsan";
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
        variables.put("money",200);
        //任务负责人
        String assignee = "zhangsan";
        List<Task> list = taskService.createTaskQuery().taskAssignee(assignee).list();
        for (Task task : list) {
            taskService.complete(task.getId(),variables);
        }
    }



}
