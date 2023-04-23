package com.example.aulas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AulasApplication {

	public static void main(String[] args) {
		SpringApplication.run(AulasApplication.class, args);
	}

}
