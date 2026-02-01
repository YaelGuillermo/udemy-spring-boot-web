package com.ideapool.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ideapool.springboot.app.models.entity.Client;

public interface IClientService {
	
	public List<Client> findAll();
	
	public Page<Client> findAll(Pageable pegeable);
	
	public Client findOne(Long id);
	
	public void save(Client client);
	
	public void delete(Long id);
}
