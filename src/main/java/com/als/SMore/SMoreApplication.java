package com.als.SMore;

import jakarta.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SMoreApplication {

	@PostConstruct
	public void started(){
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));

	}

	public static void main(String[] args) {
		SpringApplication.run(SMoreApplication.class, args);
		System.out.println("현재시각 :" + new Date());
	}
}
