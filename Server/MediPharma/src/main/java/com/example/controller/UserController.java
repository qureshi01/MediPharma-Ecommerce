package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.AddUserRequest;
import com.example.dto.UserLoginRequest;
import com.example.dto.UserResponse;
import com.example.resource.UserResource;

@RestController
@CrossOrigin("*")
@RequestMapping("api/user")
public class UserController {
	
	@Autowired
	UserResource userResource;
	
	
	@PostMapping("register")
	public ResponseEntity<UserResponse> registerUser(@RequestBody AddUserRequest userRequest) {
		return this.userResource.registerUser(userRequest);
	}

	@PostMapping("login")
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserLoginRequest loginRequest) {
		return this.userResource.loginUser(loginRequest);
	}

	
	@GetMapping("fetch")
	public ResponseEntity<UserResponse> fetchUserById(@RequestParam("userId") Integer userId) {
		return this.userResource.fetchUserById(userId);
	}
	
	
	}

