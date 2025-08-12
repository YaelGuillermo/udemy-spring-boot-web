package com.ideapool.springboot.form.app.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.ideapool.springboot.form.app.editors.CapitalizedNameEditor;
import com.ideapool.springboot.form.app.models.domain.User;
import com.ideapool.springboot.form.app.validation.UserValidator;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("user")
public class FormController {
	
	@Autowired
	private UserValidator validator;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(validator);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, "birthdate", new CustomDateEditor(dateFormat, false));
		
		binder.registerCustomEditor(String.class, "name", new CapitalizedNameEditor());
		binder.registerCustomEditor(String.class, "lastName", new CapitalizedNameEditor());
	}
	
	@GetMapping("/form")
	public String form(Model model) {
		User user = new User();
		user.setName("Jhon");
		user.setLastName("Doe");
		user.setIdentifier("123.456.789-K");
		
		model.addAttribute("title", "User form");
		model.addAttribute("user", user);
		
		return "form";
	}
	
	@PostMapping("/form")
	public String create(@Valid User user, BindingResult result, Model model, SessionStatus status) {
		model.addAttribute("title", "Result form");
		
		if(result.hasErrors()) {
			/*Map<String, String> errors = new HashMap<>();
			result.getFieldErrors().forEach(err ->{
				errors.put(err.getField(), "The field ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});
			model.addAttribute("error", errors);*/
			return "form";
		}
		
		model.addAttribute("user", user);
		status.setComplete();
		return "result";
	}
}
