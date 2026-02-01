package com.ideapool.springboot.error.app.errors;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String id) {
		super("User with ID: ".concat(id).concat(" does not exist."));
	}

}
