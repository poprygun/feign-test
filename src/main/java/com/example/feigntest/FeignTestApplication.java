package com.example.feigntest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FeignTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeignTestApplication.class, args);
	}

}



