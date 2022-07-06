package com.example.securityjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class SpringBootSecurity2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurity2Application.class, args);
	}

}
