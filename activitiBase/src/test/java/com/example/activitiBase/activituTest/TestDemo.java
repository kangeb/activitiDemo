package com.example.activitiBase.activituTest;

import com.example.activitiBase.ActivitiBaseApplicationTests;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.junit.jupiter.api.Test;

public class TestDemo extends ActivitiBaseApplicationTests {

    @Test
    public void testCreateDbTable() {
        //使用classpath下的activiti.cfg.xml中的配置创建processEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println(processEngine);
    }
}
