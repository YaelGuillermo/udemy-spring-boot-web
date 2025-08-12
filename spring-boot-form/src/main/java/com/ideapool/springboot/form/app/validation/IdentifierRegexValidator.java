package com.ideapool.springboot.form.app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IdentifierRegexValidator implements ConstraintValidator<IdentifierRegex, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value.matches("[\\d]{2}[.][\\d]{3}[.][\\d]{3}[-][A-Z]{1}")) {
			return true;
		}		
		return false;
	}
	
}
