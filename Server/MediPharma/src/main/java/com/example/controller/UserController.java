package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;
import com.example.repo.UserRepo;
import com.example.service.UserService;

@RestController
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private UserService service;
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody User user) {
		repo.save(user);
		return new ResponseEntity<>("Data Added",HttpStatus.CREATED);
	}
	
	@GetMapping("/view")
	public ResponseEntity<List<User>> viewAll() {
	        List<User> users= service.getUsers();
	        return new ResponseEntity<>(users,HttpStatus.OK);
	    }
	}

