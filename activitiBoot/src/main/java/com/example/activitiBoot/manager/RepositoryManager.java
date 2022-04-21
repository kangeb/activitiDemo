package com.example.activitiBoot.manager;


import org.activiti.engine.RepositoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RepositoryManager {

    @Resource
    private RepositoryService repositoryService;

    public void creatDeploy(){

    }


}
