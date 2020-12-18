package com.sales.online;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SalesOnlineWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesOnlineWebApplication.class, args);
	}

}
