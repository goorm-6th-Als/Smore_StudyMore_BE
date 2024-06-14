package com.als.SMore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SMoreApplication {
	public static void main(String[] args) {
		SpringApplication.run(SMoreApplication.class, args);
	}
}
