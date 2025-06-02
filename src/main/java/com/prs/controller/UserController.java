package com.prs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prs.db.UserRepo;
import com.prs.dto.LoginDto;
import com.prs.model.User;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserRepo userRepo;

	@GetMapping("/")
	public List<User> getAll() {
		return userRepo.findAll();
		// getById -"/api/users/{id}"
		// - return:Actor
	}

	@GetMapping("/{id}")
	public Optional<User> getById(@PathVariable int id) {
		// check if user exists for id
		// if yes, return user
		// if no, return NotFound
		Optional<User> u = userRepo.findById(id);
		if (u.isPresent()) {
			return u;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for id " + id);
		}
	}
	// post -"/api/users"(user will be in the RequestBody

	@PostMapping("")
	public User add(@RequestBody User user) {
		return userRepo.save(user);
		
		//@PostMapping("")
		//public User add(@RequestBody User user) {
		    //User user = new User();
		   // user.setUsername(userDTO.getUsername());
		    //user.setPassword(userDTO.getPassword());
		    //return userRepo.save(user);
		    

		}	
	
	
	// put -"/api/users/{id}"(user passed in RB)
	// - return: NOcontent()

	@PutMapping("/{id}")
	public void update(@PathVariable int id, @RequestBody User user) {
		if (id != user.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id mismatch vs URL.");
		} else if (userRepo.existsById(user.getId())) {
			userRepo.save(user);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for id " + id);
		}
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		if (userRepo.existsById(id)) {
			userRepo.deleteById(id);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for id " + id);
		}
	}

	// User login
	@PostMapping("/login")
	public User login(@RequestBody LoginDto loginDto) {
		Optional<User> userOpt = userRepo.findByUsername(loginDto.getUsername());
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			if (user.getPassword().equals(loginDto.getPassword())) {
				return user;
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found  ");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found  ");
		}
	}

}