package com.prs.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.model.LineItem;

public interface LineItemRepo extends JpaRepository<LineItem, Integer> {
	// This interface will automatically provide CRUD operations for LineItem entity
	// No need to implement any methods here

}
