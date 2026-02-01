package com.ideapool.springboot.error.app.controllers;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ideapool.springboot.error.app.errors.UserNotFoundException;

@ControllerAdvice
public class ErrorHandlerController {
	
	@ExceptionHandler({ArithmeticException.class})
	public String arithmeticError(ArithmeticException ex, Model model) {
		model.addAttribute("error", "Arithmetic error");
		model.addAttribute("message", ex.getMessage());
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("timestamp", new Date());

		return "error/generic";
	}
	
	@ExceptionHandler({NumberFormatException.class})
	public String numberFormatError(NumberFormatException ex, Model model) {
		model.addAttribute("error", "Number format error");
		model.addAttribute("message", ex.getMessage());
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("timestamp", new Date());

		return "error/generic";
	}
	
	@ExceptionHandler({UserNotFoundException.class})
	public String userNotFoundError(UserNotFoundException ex, Model model) {
		model.addAttribute("error", "User not found error");
		model.addAttribute("message", ex.getMessage());
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("timestamp", new Date());

		return "error/user";
	}
}
