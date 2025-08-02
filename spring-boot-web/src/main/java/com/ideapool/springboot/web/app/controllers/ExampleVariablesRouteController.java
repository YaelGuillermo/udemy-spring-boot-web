package com.ideapool.springboot.web.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/variables")
public class ExampleVariablesRouteController {

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("title", "Send params to route (@PathVariable)");
		return "variables/index";
	}
		
	@GetMapping("/string/{text}")
	public String variables(@PathVariable String text, Model model) {
		model.addAttribute("title", "Receive params from route (@PathVariable)");
		model.addAttribute("result", "The text send in the route is: " + text);
		return "variables/view";
	}
	
	@GetMapping("/string/{text}/{number}")
	public String variables(@PathVariable String text, @PathVariable Integer number, Model model) {
		model.addAttribute("title", "Receive params from route (@PathVariable)");
		model.addAttribute("result", "The text send in the route is: " + text
				+ "and the number in the path is: " + number);
		return "variables/view";
	}
}
