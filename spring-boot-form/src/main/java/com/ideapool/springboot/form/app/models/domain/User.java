package com.ideapool.springboot.form.app.models.domain;

import java.util.Date;
import java.util.List;

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
	
	@NotNull
	private Country country;
	
	@NotEmpty
	private List<Role> roles;
	
	private Boolean isActive;
	
	@NotEmpty
	private String genre;
	
	private String secretValue;
	
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

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getSecretValue() {
		return secretValue;
	}

	public void setSecretValue(String secretValue) {
		this.secretValue = secretValue;
	}
}
