package com.project.ModuleUser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication(scanBasePackages = "com.project.ModuleUser")
@EnableDiscoveryClient // <--- Active le client de découverte Eureka

public class ModuleUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModuleUserApplication.class, args);
	}

}
