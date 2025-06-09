package com.be.prac_toon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class PracToonApplication {

	@RequestMapping("/")
	String home() {
		return "Hello HelloHelloHelloHelloHello";
	}

	public static void main(String[] args) {
		SpringApplication.run(PracToonApplication.class, args);
	}

}