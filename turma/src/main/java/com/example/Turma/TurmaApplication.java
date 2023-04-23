package com.example.Turma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TurmaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TurmaApplication.class, args);
	}

}
