package com.ideapool.springboot.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ideapool.springboot.app.models.entity.Invoice;

public interface IInvoiceDao extends JpaRepository<Invoice, Long>{

	  @Query("""
	    select distinct i from Invoice i
	    join fetch i.client c
	    left join fetch i.items it
	    left join fetch it.product
	    where i.id = :id
	  """)
	  Invoice fetchByIdWithItems(@Param("id") Long id);
	}
