package com.capstone.dangdang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DangdangApplication {

	public static void main(String[] args) {
		SpringApplication.run(DangdangApplication.class, args);
	}

}