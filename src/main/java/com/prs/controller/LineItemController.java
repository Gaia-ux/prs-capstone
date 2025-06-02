package com.prs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.prs.db.ProductRepo;
import com.prs.db.RequestRepo;
import com.prs.model.LineItem;
import com.prs.model.Product;
import com.prs.model.Request;

 

@CrossOrigin
@RestController
@RequestMapping("/api/lineitems")
public class LineItemController {
	@Autowired
	private LineItemRepo lineItemRepo;
	@Autowired
	private RequestRepo requestRepo;
	@Autowired
	private ProductRepo productRepo;

	private boolean recalculateRequestTotal(int requestId) {
		// read the request
		Optional<Request> optRequest = requestRepo.findById(requestId);
		// check that we read a request
		if(optRequest.isEmpty()) {
			return false;
		}
		Request request = optRequest.get();
		// get the requestlines from the request
		Iterable<LineItem> lineitems =lineItemRepo.findByRequestId(requestId);
		// calculate the total
		double total = 0;
		for(LineItem l : lineitems) {
			// fill an empty product instance
			Product product = productRepo.findById(l.getProductId()).get();
			total += l.getQuantity() * product.getPrice();
		}
		request.setTotal(total);
		requestRepo.save(request);
		return true;
	}
	@GetMapping() 
	public List<LineItem> getAll() { return
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
public ResponseEntity<LineItem> add(@RequestBody LineItem lineitem) {
	LineItem li = lineItemRepo.save(lineitem);
	if(!recalculateRequestTotal(lineitem.getRequestId())) {
		return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
	}
	return new ResponseEntity<LineItem>(li, HttpStatus.CREATED);
}

@PutMapping("{id}")
public ResponseEntity update(@PathVariable int id, @RequestBody LineItem lineItem) {
	if (id != lineItem.getId()) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "LineItem id mismatch vs URL.");
	} else if (lineItemRepo.existsById(lineItem.getId())) {
		lineItemRepo.save(lineItem);
		if(!recalculateRequestTotal(lineItem.getRequestId())) {
			return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
		}
		return new ResponseEntity<LineItem>(lineItem, HttpStatus.NO_CONTENT);

	} else {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found for id " + id);
	}
}

@DeleteMapping("{id}")
public ResponseEntity delete(@PathVariable int id) {
	if (!lineItemRepo.existsById(id)) {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	Optional<LineItem> li = getById(id);
	if(li.isEmpty())
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	int requestId = li.get().getRequestId();
	lineItemRepo.deleteById(id);
	if(!recalculateRequestTotal(requestId)) {
		return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
	}
	return new ResponseEntity(HttpStatus.NO_CONTENT);
		

} 
}




