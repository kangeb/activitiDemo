package com.example.activitiBoot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
@MapperScan("com.example.activitiBoot.Mapper")//告诉mapper所在的包名
public class ActivitiBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivitiBootApplication.class, args);
	}

}
