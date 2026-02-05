package com.ideapool.springboot.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ideapool.springboot.app.models.entity.Client;

public interface IClientDao extends JpaRepository<Client, Long>{
	
	@Query("""
      select distinct c from Client c
      left join fetch c.invoices i
      where c.id = :id
	""")
	public Client fetchByIdWithInvoices(@Param("id") Long id);
	  
}
