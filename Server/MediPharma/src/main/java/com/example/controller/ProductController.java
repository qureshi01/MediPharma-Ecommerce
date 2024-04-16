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

import com.example.model.Product;
import com.example.model.User;
import com.example.repo.ProductRepo;
import com.example.service.ProductService;

@RestController
@CrossOrigin("*")
public class ProductController {
	
	@Autowired
	private ProductRepo repo;
	
	@Autowired
	private ProductService service;

	@PostMapping("/addProduct")
	public ResponseEntity<String> addProduct(@RequestBody Product product) {
		repo.save(product);
		return new ResponseEntity<>("Product Added",HttpStatus.CREATED);
	}
	
	@GetMapping("/viewProduct")
	public ResponseEntity<List<Product>> viewAll() {
	        List<Product> users= service.getProducts();
	        return new ResponseEntity<>(users,HttpStatus.OK);
	    }
}
