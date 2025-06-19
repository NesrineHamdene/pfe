package com.project.ModuleReclamation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.project.ModuleReclamation.feign", "com.project.ModuleShared.feign"})

public class ModuleReclamationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModuleReclamationApplication.class, args);
	}

}
