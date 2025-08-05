package com.ideapool.springboot.form.app.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ideapool.springboot.form.app.models.domain.User;

import jakarta.validation.Valid;

@Controller
public class FormController {
	
	@GetMapping("/form")
	public String form(Model model) {
		model.addAttribute("title", "User form");
		model.addAttribute("user", new User());
		
		return "form";
	}
	
	@PostMapping("/form")
	public String create(@Valid User user, BindingResult result, Model model) {

		model.addAttribute("title", "Result form");
		
		if(result.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			result.getFieldErrors().forEach(err ->{
				errors.put(err.getField(), "The field ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});
			model.addAttribute("error", errors);
			return "form";
		}
		
		model.addAttribute("user", user);

		return "result";
	}
}
