package com.ideapool.springboot.error.app.services;

import java.util.List;
import java.util.Optional;

import com.ideapool.springboot.error.app.models.domain.User;

public interface UserService {
	
	public List<User> list();
	public User getById(Integer id);
	public Optional<User> getByIdOptional(Integer id);
}
