package com.ideapool.springboot.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideapool.springboot.app.models.entity.User;

public interface IUserDao extends JpaRepository<User, Long>{

	public User findByUsername(String username);
}
