package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepo;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepo repo;
	
	//get all users
	@GetMapping
	public List<User> getAllUsers(){
		return repo.findAll();
	}
	
	//get user by id
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public User getUserById(@PathVariable (value = "id") long userId) {
		return repo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));
	}
	
	//create user
	@PostMapping
	public User createUser(@RequestBody User user) {
		return repo.save(user);
	}
	
	//update user
	@RequestMapping(method = RequestMethod.PUT)
	public User updateUser(@RequestBody User user, @PathVariable (value = "id") long userId ) {
		User existUser = repo.findById(userId).orElse(new User(null,null,null));
		existUser.setFirstName(user.getFirstName());
		existUser.setLastName(user.getLastName());
		existUser.setEmail(user.getEmail());
		return repo.save(existUser);
	}
	
	//delete user by id
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUSerById(@PathVariable (value="id") long userId){
		User user = repo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));
		repo.delete(user);
		return ResponseEntity.ok().build();
	}
	
	
}
