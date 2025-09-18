package com.matchacloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SportCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(SportCenterApplication.class, args);
        System.out.println("体育中心启动成功!");
    }
}