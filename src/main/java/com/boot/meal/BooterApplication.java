package com.boot.meal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@ComponentScan({"com.boot.meal.common","com.boot.agora"})
@Controller
@SpringBootApplication
public class BooterApplication {

	public static void main(String[] args) {
//		System.setProperty("spring.devtools.restart.enables","false");
//		System.setProperty("spring.devtools.livereload","true");
		SpringApplication.run(BooterApplication.class, args);
		log.info("시스템 구동 완료");
	}

	@GetMapping("/home")
	public String home(){
		return "/page/home";
	}


}