package com.example.activitiBase.config;

import org.activiti.engine.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivitiConfig {

    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

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
