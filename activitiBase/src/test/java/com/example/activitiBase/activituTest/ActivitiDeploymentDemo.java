package com.example.activitiBase.activituTest;

import com.example.activitiBase.ActivitiBaseApplicationTests;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.List;

public class ActivitiDeploymentDemo extends ActivitiBaseApplicationTests {

    @Resource
    RepositoryService repositoryService;

    @Test
    public void testDeployment(){

//        3、使用RepositoryService进行部署
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("bpmn/evection.bpmn") // 添加bpmn资源
                .addClasspathResource("bpmn/evection.png")  // 添加png资源
                .name("出差申请流程")
                .key("ProcessDemo")
                .deploy();
//        4、输出部署信息
        System.out.println("流程部署id：" + deployment.getId());
        System.out.println("流程部署名称：" + deployment.getName());
    }

    @Test
    public void testquery(){
        // 创建流程定义查询
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        // 查询返回列表
        List<ProcessDefinition> definitionList = processDefinitionQuery.processDefinitionKey("myProcess_1").orderByProcessDefinitionVersion().desc().list();
        //输出流程定义信息
        for (ProcessDefinition processDefinition : definitionList) {
            System.out.println("流程定义 id="+processDefinition.getId());
            System.out.println("流程定义 name="+processDefinition.getName());
            System.out.println("流程定义 key="+processDefinition.getKey());
            System.out.println("流程定义 Version="+processDefinition.getVersion());
            System.out.println("流程部署ID ="+processDefinition.getDeploymentId());
        }
    }

    @Test
    public void testDelete(){
        repositoryService.deleteDeployment("100001",true);
    }

}
