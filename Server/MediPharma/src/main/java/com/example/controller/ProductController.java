package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.ProductDao;




@RestController
@CrossOrigin("*")
public class ProductController {
	
	@Autowired
	private ProductDao productDao;
	
	
}
