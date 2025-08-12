package com.ideapool.springboot.form.app.models.domain;

import java.util.Date;

//import org.springframework.format.annotation.DateTimeFormat;

import com.ideapool.springboot.form.app.validation.IdentifierRegex;
import com.ideapool.springboot.form.app.validation.Required;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class User {

	@IdentifierRegex
	private String identifier;
	
	//@NotEmpty(message = "The name cant be a empty name")
	private String name;
	
	@Required
	private String lastName;

	@NotBlank
	@Size(min = 3, max = 8)
	private String username;
	
	@Required
	@Email
	private String email;
	
	@NotEmpty(message = "The password cant be a empty name")
	private String password;

	@NotNull
	@Min(5)
	@Max(5000)
	private Integer account;

	@NotNull
	@Past
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthdate;
	
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

	public Integer getAccount() {
		return account;
	}

	public void setAccount(Integer account) {
		this.account = account;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
}
