package com.example.activitiBase.test;

import com.alibaba.fastjson.JSONObject;
import com.example.activitiBase.ActivitiBaseApplicationTests;
import com.example.activitiBase.Mapper.UserMapper;
import com.example.activitiBase.domain.User;

import org.activiti.engine.RuntimeService;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.List;

public class TestDB extends ActivitiBaseApplicationTests {
    @Resource
    private UserMapper userMapper;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Test
    public void test(){
        List<User> result = userMapper.queryAll();
        System.out.println(JSONObject.toJSONString(result));
    }

    @Test
    public void test2(){
//        Runnable runnabl = new Runnable() {
//            @Override
//            public void run() {
//                test3();
//            }
//        } ;
//        Thread thread = new Thread(runnabl);
//        thread.start();
        threadPoolTaskExecutor.execute(()->{
            test3();
        });
        System.out.println("lailailai1");
    }

    public void test3(){
        System.out.println("ninini");
        throw new RuntimeException("异常");
    }
}
