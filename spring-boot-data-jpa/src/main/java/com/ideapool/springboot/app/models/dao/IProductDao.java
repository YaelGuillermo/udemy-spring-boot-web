package com.ideapool.springboot.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ideapool.springboot.app.models.entity.Product;

public interface IProductDao extends JpaRepository<Product, Long>{

	@Query("select p from Product p where p.name like %?1%")
	public List<Product> findByName(String term);
	
	public List<Product> findByNameLikeIgnoreCase(String term);
}
