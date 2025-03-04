package com.project.ModuleUser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.project.ModuleUser")

public class ModuleUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModuleUserApplication.class, args);
	}

}
