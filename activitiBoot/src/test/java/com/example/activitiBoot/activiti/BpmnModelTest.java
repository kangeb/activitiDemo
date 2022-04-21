package com.example.activitiBoot.activiti;

import com.alibaba.fastjson.JSONObject;
import com.example.activitiBoot.ActivitiBootApplicationTests;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BpmnModelTest extends ActivitiBootApplicationTests {

    @Resource
    private RepositoryService repositoryService;

    @Resource
    TaskService taskService;

    @Resource
    RuntimeService runtimeService;

    @Resource
    HistoryService historyService;

    @Test
    public void demoTest() {
        // 创建开始
        StartEvent startEvent = new StartEvent();
        startEvent.setId("startEvent");
        startEvent.setName("startEvent");
        // 创建初级审批人
        UserTask first = new UserTask();
        first.setId("first");
        first.setName("first");
        first.setAssignee("zhangyi");
        // 创建次级审批成员
        UserTask second = new UserTask();
        second.setId("second");
        second.setName("second");
        second.setAssignee("zhanger");
        // 创建结束点
        EndEvent endEvent = new EndEvent();
        endEvent.setId("endEvent");
        endEvent.setName("endEvent");

        // 创建连线
        SequenceFlow s1 = new SequenceFlow();
        s1.setId("s1");
        s1.setName("s1");
        s1.setSourceRef("startEvent");
        s1.setTargetRef("first");

        SequenceFlow s2 = new SequenceFlow();
        s2.setId("s2");
        s2.setName("s2");
        s2.setSourceRef("first");
        s2.setTargetRef("second");

        SequenceFlow s3 = new SequenceFlow();
        s3.setId("s3");
        s3.setName("s3");
        s3.setSourceRef("second");
        s3.setTargetRef("endEvent");

        // 连接
        List<SequenceFlow> start2first = new ArrayList<>();
        start2first.add(s1);
        startEvent.setOutgoingFlows(start2first);
        first.setIncomingFlows(start2first);

        List<SequenceFlow> first2Sencond = new ArrayList<>();
        first2Sencond.add(s2);
        first.setOutgoingFlows(first2Sencond);
        second.setIncomingFlows(first2Sencond);

        List<SequenceFlow> second2End = new ArrayList<>();
        second2End.add(s3);
        second.setOutgoingFlows(second2End);
        endEvent.setIncomingFlows(second2End);

        // 创建流程
        Process process = new Process();
        process.setName("zhenyi02");
        process.setId("zhenyi02");
        process.addFlowElement(startEvent);
        process.addFlowElement(first);
        process.addFlowElement(second);
        process.addFlowElement(endEvent);
        process.addFlowElement(s1);
        process.addFlowElement(s2);
        process.addFlowElement(s3);

        // 创建Bpmnmodel
        BpmnModel bpmnModel = new BpmnModel();
        bpmnModel.addProcess(process);

        Deployment deployment = repositoryService.createDeployment()
                .name("bpmn")
                .addBpmnModel("bpmnModel.bpmn", bpmnModel) // 这个addBpmnModel第一个参数一定要带后缀.bpmn
                .key("zhenyi02key")
                .tenantId("zhenyi02tenantId")
                .deploy();

        System.out.println("流程部署id：" + deployment.getId());
        System.out.println("流程部署名称：" + deployment.getName());


    }

    @Test
    public void testStartProcess(){
        Map<String, Object> variables = Maps.newHashMap();
        variables.put("money",200);
//        3、根据流程定义Id启动流程
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceById("CandidateGroupsId:1:35003","buskey",variables);
//        输出内容
        System.out.println("流程定义id：" + processInstance.getProcessDefinitionId());
        System.out.println("流程实例id：" + processInstance.getId());
        System.out.println("当前活动Id：" + processInstance.getActivityId());

        //repositoryService.changeDeploymentTenantId("deploymentId","newTenantId");


    }

    @Test
    public void test(){
        taskService.claim("taskId","userId");
        taskService.setAssignee("taskId","userId");
//        ExecutionQuery executionQuery = runtimeService.createExecutionQuery();
//        List<Execution> result =  executionQuery.processInstanceBusinessKey("buskey").list();
//        for (Execution execution : result) {
//            System.out.println(execution.getId());
//        }
    }

    @Test
    public void query(){

        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> definitionList = processDefinitionQuery.processDefinitionKey("CandidateGroupsId").orderByProcessDefinitionVersion().desc().list();
//        for (ProcessDefinition processDefinition : definitionList) {
//            repositoryService.activateProcessDefinitionById(processDefinition.getId());
////            System.out.println("流程定义 id="+processDefinition.getId());
////            System.out.println("流程定义 name="+processDefinition.getName());
////            System.out.println("流程定义 key="+processDefinition.getKey());
////            System.out.println("流程定义 Version="+processDefinition.getVersion());
////            System.out.println("流程部署ID ="+processDefinition.getDeploymentId());
//        }

        //definitionList = processDefinitionQuery.processDefinitionKey("exclusiveGatewayId").orderByProcessDefinitionVersion().desc().list();
        for (ProcessDefinition processDefinition : definitionList) {
            Map<String, Object> variables = Maps.newHashMap();
            variables.put("money",200);
//        3、根据流程定义Id启动流程
            ProcessInstance processInstance = runtimeService
                    .startProcessInstanceById(processDefinition.getId(),variables);
//        输出内容
            System.out.println("流程定义id：" + processInstance.getProcessDefinitionId());
            System.out.println("流程实例id：" + processInstance.getId());
            System.out.println("当前活动Id：" + processInstance.getActivityId());

//            System.out.println("流程定义 id="+processDefinition.getId());
//            System.out.println("流程定义 name="+processDefinition.getName());
//            System.out.println("流程定义 key="+processDefinition.getKey());
//            System.out.println("流程定义 Version="+processDefinition.getVersion());
//            System.out.println("流程部署ID ="+processDefinition.getDeploymentId());
//            System.out.println("流程挂起 ="+processDefinition.isSuspended());

        }

        //


    }

    @Test
    public void testFindPersonalTaskList() {
//        任务负责人
        String assignee = "zhangsan";
//        根据流程key 和 任务负责人 查询任务
        List<Task> list = taskService.createTaskQuery()//.taskCandidateOrAssigned("q")
                //.processDefinitionKey("zhenyi01") //流程Key
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
    public void testTaskCompte() {
//        任务负责人
        String assignee = "wangwu";
//        根据流程key 和 任务负责人 查询任务
        List<Task> list = taskService.createTaskQuery()
                //.processDefinitionKey("zhenyi01") //流程Key
                .taskAssignee(assignee)//只查询该任务负责人的任务
                .list();

        for (Task task : list) {
            Map<String, Object> variables = Maps.newHashMap();
            variables.put("money",200);

            taskService.complete(task.getId(),variables);
        }
    }

    @Test
    public void demoTest2() {
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
        zhangsanUserTask.setCandidateUsers(Lists.newArrayList("zhangyi","zhanger"));
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
        Process process = new Process();
        process.setName("lastCandi");
        //重要
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
                .key("CandidateGroupsIdKey")
                .tenantId("kangerbaoTest")
                .deploy();

        System.out.println("流程部署id：" + deployment.getId());
        System.out.println("流程部署名称：" + deployment.getName());


    }


}
