package com.ideapool.springboot.web.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/params")
public class TestParamsController {
	
		@GetMapping("/")
		public String index() {
			return "params/index";
		}

		@GetMapping("/string")
		public String param(@RequestParam(name="text", required=false, defaultValue="any value") String text, Model model) {
			model.addAttribute("result", "The text received is: " + text);
			
			return "params/view";
		}
}
