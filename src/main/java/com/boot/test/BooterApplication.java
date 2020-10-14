package com.boot.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.test.api.domain.Member;

@RestController
@SpringBootApplication
public class BooterApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooterApplication.class, args);
	}
	

	@GetMapping("/test")
	public Object ready() {
		Member member = new Member();
		return "I'm ready";
	} 
	

}