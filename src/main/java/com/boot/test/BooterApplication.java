package com.boot.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class BooterApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooterApplication.class, args);
	}
	
	@GetMapping("/test")
	public Object ready() {
		return "I'm ready";
	} 
	

}