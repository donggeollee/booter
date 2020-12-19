package com.boot.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class BooterApplication {

	@Autowired
	Environment environment;

	@Value("${spring.profiles.active}")
	String activeProfile;

	public static void main(String[] args) {
		SpringApplication.run(BooterApplication.class, args);
	}

	@GetMapping("/")
	public String home(){
		return "hello booter";
	}
}