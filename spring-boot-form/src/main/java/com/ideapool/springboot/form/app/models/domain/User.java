package com.ideapool.springboot.form.app.models.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class User {

	//@Pattern(regexp = "[\\d]{2}[.][\\d]{3}[.][\\d]{3}[-][A-Z]{1}")
	private String identifier;
	
	//@NotEmpty(message = "The name cant be a empty name")
	private String name;
	
	@NotEmpty(message = "The last name cant be a empty name")
	private String lastName;

	@NotBlank
	@Size(min = 3, max = 8)
	private String username;
	
	@NotEmpty
	@Email
	private String email;
	
	@NotEmpty(message = "The password cant be a empty name")
	private String password;
	
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
