package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Product;
import com.example.repo.ProductRepo;

@RestController
@CrossOrigin("*")
public class ProductController {
	
	@Autowired
	private ProductRepo repo;

	@PostMapping("/addProduct")
	public ResponseEntity<String> addProduct(@RequestBody Product product) {
		repo.save(product);
		return new ResponseEntity<>("Product Added",HttpStatus.CREATED);
	}
}
