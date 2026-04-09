package com.ideapool.springboot.app.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String login(
			Model model, 
			Principal principal, 
			RedirectAttributes flash,
			@RequestParam(value="error", required=false) String error,
			@RequestParam(value="logout", required=false) String logout) {		
		if (principal != null) {
			flash.addFlashAttribute("info", "You have already logged in previously.");
			return "redirect:/list";
		}
		if (error != null) {
			flash.addFlashAttribute("error", "Incorrect username or password.");
		}
		if (logout != null) {
			flash.addFlashAttribute("info", "You have successfully logged out.");
		}
		return "login";
	}
}
