package com.ideapool.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ideapool.springboot.app.models.entity.Client;
import com.ideapool.springboot.app.models.entity.Invoice;
import com.ideapool.springboot.app.models.entity.Product;

public interface IClientService {
	
	public List<Client> findAll();
	
	public Page<Client> findAll(Pageable pegeable);
	
	public Client findOne(Long id);
	
	public void save(Client client);
	
	public void delete(Long id);
	
	public List<Product> finProductByName(String term);
	
	public void saveInvoice(Invoice invoice);
	
	public Product findProductById(Long id);
	
	public Invoice findInvoiceById(Long id);
	
	public void deleteInvoice(Long id);
	
	public Invoice findInvoiceByIdWithItems(Long id);
}
