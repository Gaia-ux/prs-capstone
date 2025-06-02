package com.prs.controller;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

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

import com.prs.db.RequestRepo;
import com.prs.db.UserRepo;
import com.prs.model.Request;
import com.prs.model.RequestDTO;
import com.prs.model.User;

@CrossOrigin
@RestController

@RequestMapping("/api/requests")
public class RequestController {

	@Autowired
	private RequestRepo requestRepo;

	@Autowired
	private UserRepo userRepo;

	@GetMapping
	public ResponseEntity<Iterable<Request>> getRequests() {
		Iterable<Request> requests = requestRepo.findAll();
		return new ResponseEntity<Iterable<Request>>(requests, HttpStatus.OK);
	}

	@GetMapping("{id}")

	public ResponseEntity<Request> getRequest(@PathVariable int id) {
		Optional<Request> request = requestRepo.findById(id);
		if (request.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Request>(request.get(), HttpStatus.OK);
	}

	@GetMapping("reviews/{userid}")
	public ResponseEntity<Iterable<Request>> getRequestsInReview(@PathVariable int userid) {
 		Iterable<Request> requests = requestRepo.findByStatusAndUserIdNot("REVIEW", userid);
		return new ResponseEntity<Iterable<Request>>(requests, HttpStatus.OK);
	}

	public ResponseEntity<Request> postRequest(@RequestBody Request request) {
		Request newRequest = requestRepo.save(request);
		return new ResponseEntity<Request>(newRequest, HttpStatus.CREATED);

	}

	@PostMapping
	public ResponseEntity<?> createRequest(@RequestBody RequestDTO requestDTO) {
		// Fetch the user
		Optional<User> optionalUser = userRepo.findById(requestDTO.getUserId());
		if (!optionalUser.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid UserId");
		}

		User user = optionalUser.get();

		// Create and populate Request entity
		Request request = new Request();
		request.setUserId(user.getId());
		request.setDescription(requestDTO.getDescription());
		request.setJustification(requestDTO.getJustification());
		request.setDateNeeded(requestDTO.getDateNeeded());
		request.setDeliveryMode(requestDTO.getDeliveryMode());
		request.setStatus("NEW");
		request.setTotal(0.0); // Default
		request.setSubmittedDate(LocalDateTime.now());
		request.setReasonForRejection(null); // Not rejected yet
		request.setRequestNumber(generateRequestNumber());

		// Save to DB
		requestRepo.save(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(request);
	}

	private String generateRequestNumber() {
		Random rand = new Random();
		int i = rand.nextInt(100) + 1;

		return "REQ-00" + String.valueOf(i);
	}

	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putRequest(@PathVariable int id, @RequestBody Request request) {
		if (id != request.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request id mismatch vs URL.");
		} else if (requestRepo.existsById(request.getId())) {
			requestRepo.save(request);

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id " + id);
		}
	}

	@SuppressWarnings("rawtypes")
	@PutMapping("reject/{id}")
	public ResponseEntity rejectRequest(@PathVariable int id, @RequestBody Request request) {
		request.setStatus("REJECTED");
		return putRequest(id, request);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("approve/{id}")
	public ResponseEntity approveRequest(@PathVariable int id, @RequestBody Request request) {
		request.setStatus("APPROVED");
		return putRequest(id, request);
	}

	@SuppressWarnings("rawtypes")
	@PutMapping("review/{id}")
	public ResponseEntity reviewRequest(@PathVariable int id, @RequestBody Request request) {
		request.setStatus(request.getTotal() <= 50 ? "APPROVED" : "REVIEW");
		return putRequest(id, request);
	}

	@SuppressWarnings("rawtypes")

	// @DeleteMapping("{id}")
//     public void delete(@PathVariable int id) {
//     	if (requestRepo.existsById(id)) {
//     		requestRepo.deleteById(id);
//     	} else {
//     		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id " + id);

	@DeleteMapping("{id}")
	public ResponseEntity deleteRequest(@PathVariable int id) {
		Optional<Request> request = requestRepo.findById(id);
		if (request.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		requestRepo.delete(request.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
