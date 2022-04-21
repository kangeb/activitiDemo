package com.example.activitiBoot.test;

import com.alibaba.fastjson.JSONObject;
import com.example.activitiBoot.ActivitiBootApplicationTests;
import com.example.activitiBoot.Mapper.UserMapper;
import com.example.activitiBoot.domain.User;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.List;

public class TestDB extends ActivitiBootApplicationTests {
    @Resource
    private UserMapper userMapper;

    @Test
    public void test(){
        List<User> result = userMapper.queryAll();
        System.out.println(JSONObject.toJSONString(result));
    }
}
