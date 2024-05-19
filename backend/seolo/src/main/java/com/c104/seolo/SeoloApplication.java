package com.c104.seolo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SeoloApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeoloApplication.class, args);
	}
}
