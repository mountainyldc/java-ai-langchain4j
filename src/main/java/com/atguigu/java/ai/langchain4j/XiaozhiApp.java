package com.atguigu.java.ai.langchain4j;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication   // 告诉电脑：这是一个 Spring Boot 项目
@MapperScan("com.atguigu.java.ai.langchain4j.mapper")  // 扫描Mapper接口
public class XiaozhiApp {
    public static void main(String[] args) {
        SpringApplication.run(XiaozhiApp.class,args);
    }
}
