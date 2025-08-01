package com.ideapool.springboot.web.app.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ideapool.springboot.web.app.models.User;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/app")
public class IndexController {

	@Value("${text.indexcontroller.index.title}")
	private String textIndex;
	
	@Value("${text.indexcontroller.profile.title}")
	private String textProfile;
	
	@Value("${text.indexcontroller.list.title}")
	private String textList;
	
	@GetMapping({"/index", "/", "", "/home"})
	public String index(Model model) {
		model.addAttribute("title", textIndex);
		return "index";
	}
	
	@RequestMapping("/profile")
	public String profile(Model model) {
		
		User user =new User();
		user.setName("Yael");
		user.setLastName("González");
		user.setEmail("gonzalezyaelg@gmail.com");
		
		model.addAttribute("user", user);
		model.addAttribute("title", textProfile.concat(user.getName()));

		return "profile";
	}
	
	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("title", textList);
		
		return "list";
	}
		
	@ModelAttribute("users")
	public List<User> populateUsers(){
		List<User> users = Arrays.asList(
				new User("Yael", "González", "yael@gmail.com"),
				new User("Jhon", "Doe", "jhon@gmail.com"),
				new User("Fred", "Simpson", "fred@gmail.com"),
				new User("Rob", "Simpson", "rob@gmail.com")
			);
		
		return users;
	}
}
