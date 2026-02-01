package com.ideapool.springboot.error.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ideapool.springboot.error.app.errors.UserNotFoundException;
import com.ideapool.springboot.error.app.models.domain.User;
import com.ideapool.springboot.error.app.services.UserService;

@Controller
public class AppController {
	
	@Autowired
	private UserService userService;

	@SuppressWarnings("unused")
	@GetMapping("/index")
	public String index() {
		// Integer value = 100/0;
		Integer value = Integer.parseInt("10x");
		return "index";
	}
	
	@GetMapping("/view/{id}")
	public String view(@PathVariable Integer id, Model model) {
		/*
		User user = userService.getById(id);
		if (user == null) {
			throw new UserNotFoundException(id.toString());
		}
		*/
		User user = userService.getByIdOptional(id)
				.orElseThrow(() -> new UserNotFoundException(id.toString()));

		model.addAttribute("user", user);
		model.addAttribute("title", "User details: ".concat(user.getName()));
		return "view";
	}
}
