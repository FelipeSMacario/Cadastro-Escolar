package com.example.Dias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DiasApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiasApplication.class, args);
	}

}
