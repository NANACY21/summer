package com.matchacloud.summerstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SummerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SummerApplication.class, args);
		System.out.println("summer项目启动成功!");
	}

}
