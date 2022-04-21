package com.example.activitiBase.activituTest;

import com.example.activitiBase.ActivitiBaseApplicationTests;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

public class ActivitiRunDemo extends ActivitiBaseApplicationTests {

    @Resource
    RuntimeService runtimeService;

    @Test
    public void testStartProcess(){
//        3、根据流程定义Id启动流程
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceByKey("myProcess_1");
//        输出内容
        System.out.println("流程定义id：" + processInstance.getProcessDefinitionId());
        System.out.println("流程实例id：" + processInstance.getId());
        System.out.println("当前活动Id：" + processInstance.getActivityId());
    }
}
