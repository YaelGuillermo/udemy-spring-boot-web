package com.ideapool.springboot.web.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;

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
		
		@GetMapping("/mix-params")
		public String param(@RequestParam String greeting, @RequestParam Integer number, Model model) {
			model.addAttribute("result", "The greeting received is: " + greeting + " and the number is: " + number);
			
			return "params/view";
		}
		
		@GetMapping("/mix-params-request")
		public String param(HttpServletRequest request, Model model) {
			String greeting = request.getParameter("greeting");
			Integer number = null;
			try {
				number = Integer.parseInt(request.getParameter("number"));
			} catch (NumberFormatException e) {
				number = 0;
			}
			
			model.addAttribute("result", "The greeting received is: " + greeting + " and the number is: " + number);
			
			return "params/view";
		}
}
