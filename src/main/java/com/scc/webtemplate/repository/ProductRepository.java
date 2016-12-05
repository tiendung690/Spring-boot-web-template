package com.scc.webtemplate.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.scc.webtemplate.domain.Product;

public interface ProductRepository extends Repository<Product, String>{

	//@Query("delete from Product where id=?1")
	void delete(Product product);
	
	Page<Product> findAll(Pageable pageable);
	
	List<Product> findAll();
	
	@Query("select P from Product P where P.name=?1")
	Page<Product> findByQuery(Pageable pageable, String name);
	
	Product findOne(String id);
	
	Product save(Product persisted);
	
	Long count();	
	
}
