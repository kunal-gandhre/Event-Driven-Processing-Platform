package com.gandhre.demo.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EventDrivenProcessingApplication {
	public static void main(String[] args) {
		SpringApplication.run(EventDrivenProcessingApplication.class, args);
	}
}
