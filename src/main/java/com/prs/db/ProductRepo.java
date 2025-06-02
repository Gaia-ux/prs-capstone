package com.prs.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.model.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {
	// This interface will automatically provide CRUD operations for Product entity
	// No need to implement any methods here

}
