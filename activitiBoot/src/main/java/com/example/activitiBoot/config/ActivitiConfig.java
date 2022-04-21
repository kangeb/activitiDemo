package com.example.activitiBoot.config;

import org.activiti.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivitiConfig {


    private ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");

    private ProcessEngine processEngine = configuration.buildProcessEngine();


    /**
     * 管理流程运行时数据
     */

    @Bean
    public RuntimeService runtimeService(){
        return processEngine.getRuntimeService();
    }


    /**
     * 管理流程部署数据
     */
    @Bean
    public RepositoryService repositoryService(){
        return processEngine.getRepositoryService();
    }

    /**
     * 管理流程历史数据
     */
    @Bean
    public HistoryService historyService(){
        return processEngine.getHistoryService();
    }


    @Bean
    public TaskService taskService() {
        return processEngine.getTaskService();
    }

}
