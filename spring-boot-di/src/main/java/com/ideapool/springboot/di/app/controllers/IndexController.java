package com.ideapool.springboot.di.app.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ideapool.springboot.di.app.models.service.IService;

@Controller
public class IndexController {
	
	private IService service;
	
    public IndexController(IService service) {		
    	this.service = service;
	}

	@GetMapping({"/", "", "/index"})
	public String index(Model model) {
		model.addAttribute("object", service.operation());
		
		return "index";
	}
}
