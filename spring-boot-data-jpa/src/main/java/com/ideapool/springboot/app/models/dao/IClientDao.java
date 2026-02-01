package com.ideapool.springboot.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideapool.springboot.app.models.entity.Client;

public interface IClientDao extends JpaRepository<Client, Long>{
	
}
