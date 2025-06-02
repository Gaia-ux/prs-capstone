package com.prs.db;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.prs.model.Request;

public interface RequestRepo extends JpaRepository<Request, Integer> {

	Iterable<Request> findByStatusAndUserIdNot(String string, int userid);
	// Custom query methods can be defined here if needed
	//@Query("SELECT  r.requestNumber FROM request r  ORDER BY r.requestNumber DESC limit 1")
	//Optional<String> getMaxRequestNumber();
	@Query("SELECT r.requestNumber FROM Request r WHERE r.requestNumber LIKE ?1% ORDER BY r.requestNumber DESC")
	Optional<String> findMaxRequestNumberStartingWith(String prefix);

}
			
