package com.example.activitiBase;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
@MapperScan("com.example.activitiBase.Mapper")//告诉mapper所在的包名
public class ActivitiBaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivitiBaseApplication.class, args);
	}

}
