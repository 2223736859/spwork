package com.example.spwork;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.spwork.mapper")
public class SpworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpworkApplication.class, args);
    }

}
