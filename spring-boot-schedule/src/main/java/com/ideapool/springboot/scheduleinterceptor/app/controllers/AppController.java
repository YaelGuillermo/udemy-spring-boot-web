package com.ideapool.springboot.scheduleinterceptor.app.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
	
	@Value("${config.schedule.open}")
	private Integer open;
	
	@Value("${config.schedule.close}")
	private Integer close;
	
	@GetMapping({"/", "/index"})
	public String index(Model model) {
		model.addAttribute("title", "Welcome to customer service hours");
		return "index";
	}
	
	@GetMapping("close")
	public String close(Model model) {
		StringBuilder message = new StringBuilder("Closed, please visit us from ");
		message.append(open);
		message.append(" to ");
		message.append(close);
		message.append(", thanks!");
		
		model.addAttribute("title", "Outside opening hours");
		model.addAttribute("message", message.toString());
		return "close";
	}
}
