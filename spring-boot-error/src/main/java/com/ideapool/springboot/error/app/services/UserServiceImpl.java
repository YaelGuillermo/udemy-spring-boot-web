package com.ideapool.springboot.error.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ideapool.springboot.error.app.models.domain.User;

@Service
public class UserServiceImpl implements UserService {

	private List<User> list;
	
	public UserServiceImpl() {
		this.list = new ArrayList<>();
		this.list.add(new User(1, "Yael", "González"));
		this.list.add(new User(2, "Joe", "Doe"));
		this.list.add(new User(3, "Jane", "Smith"));
		this.list.add(new User(4, "Bob", "Johnson"));
		this.list.add(new User(5, "Paola", "Martínez"));
		this.list.add(new User(6, "Carlos", "Pérez"));
		this.list.add(new User(7, "Lucía", "Fernández"));
	}

	@Override
	public List<User> list() {
		return this.list;
	}

	@Override
	public User getById(Integer id) {
		User result = null;
		for(User user: this.list) {
			if(user.getId().equals(id)) {
				result = user;
				break;
			}
		}
		return result;
	}

	@Override
	public Optional<User> getByIdOptional(Integer id) {
		User user = this.getById(id);
		return Optional.ofNullable(user);
	}
	
}
