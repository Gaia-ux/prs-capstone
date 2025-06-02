package com.prs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.prs.db.LineItemRepo;
 
import com.prs.model.LineItem;

 

@CrossOrigin
@RestController
@RequestMapping("/api/lineitems")
public class LineItemController {
	@Autowired
	private LineItemRepo lineItemRepo;

@GetMapping() public List<LineItem> getAll() { return
			lineItemRepo.findAll(); }
	
	 
@GetMapping("{id}")
public Optional<LineItem> getById(@PathVariable int id) {
	// check if lineItem exists for id
	// if yes, return lineItem
	// if no, return NotFound
	Optional<LineItem> l = lineItemRepo.findById(id);
	if (l.isPresent()) {
		return l;
	} else {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lineitem not found for id " + id);
	}
}


@PostMapping()
public LineItem add(@RequestBody LineItem lineitem) {
	
	return lineItemRepo.save(lineitem);
	
}

@PutMapping("{id}")
public void update(@PathVariable int id, @RequestBody LineItem lineItem) {
	if (id != lineItem.getId()) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "LineItem id mismatch vs URL.");
	} else if (lineItemRepo.existsById(lineItem.getId())) {
		lineItemRepo.save(lineItem);
	} else {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found for id " + id);
	}
}

@DeleteMapping("{id}")
public void delete(@PathVariable int id) {
	if (lineItemRepo.existsById(id)) {
		lineItemRepo.deleteById(id);
	} else {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found for id " + id);
	}
}

}


