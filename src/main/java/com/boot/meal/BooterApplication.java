package com.boot.meal;

import com.boot.meal.api.biz.user.domain.User;
import com.boot.meal.api.biz.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SpringBootApplication
public class BooterApplication {

	@Autowired
	Environment environment;

	@Autowired
	UserRepository userRepository;

	@Value("${spring.profiles.active}")
	String activeProfile;

	public static void main(String[] args) {
		SpringApplication.run(BooterApplication.class, args);
	}

	@GetMapping("/")
	public String home(){
		return "hello booter";
	}

	@GetMapping("/post")
	public String post(@RequestParam String jsonString){
		return jsonString;
	}

	@RequestMapping("/view/user")
	public String viewMember() {
		return "Member.html View!";
	}

	@RequestMapping(value = "/api/users", method = RequestMethod.GET)
	public List<User> findMembers(){
		return userRepository.findAll();
	}

//	@RequestMapping(value = "/api/member/{idx}", method = RequestMethod.GET)
//	public User findMember(@PathVariable long idx){
//		return userRepository.findOne(idx).get();
//	}

	@RequestMapping(value = "/api/user", method = RequestMethod.POST)
	public User saveMember(@RequestBody User user){
		return userRepository.save(user);
	}

	@RequestMapping(value = "/api/user", method = RequestMethod.PUT)
	public User updateMember(@RequestBody User user){
		return userRepository.save(user);
	}

//	@RequestMapping(value = "/api/member/{idx}", method = RequestMethod.DELETE)
//	public String deleteMember(@PathVariable long idx){
//		userRepository.delete();
//		return "delete";
//	}
}