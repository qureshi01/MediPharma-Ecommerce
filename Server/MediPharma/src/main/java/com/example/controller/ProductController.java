package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CommonApiResponse;
import com.example.dto.ProductAddRequest;
import com.example.dto.ProductResponse;
import com.example.resource.ProductResource;

import jakarta.servlet.http.HttpServletResponse;




@RestController
@CrossOrigin("*")
@RequestMapping("api/product")
public class ProductController {
	
	@Autowired
	private ProductResource productResource;

	@PostMapping("add")
	public ResponseEntity<CommonApiResponse> addProduct(ProductAddRequest productDto) {
		return this.productResource.addProduct(productDto);
	}

	@GetMapping("all")
	public ResponseEntity<ProductResponse> getAllProducts() {
		return this.productResource.getAllProducts();
	}

	@GetMapping("id")
	public ResponseEntity<ProductResponse> getProductById(@RequestParam("productId") int productId) {
		return this.productResource.getProductById(productId);
	}

	@GetMapping("category/fetch")
	public ResponseEntity<?> getProductsByCategories(@RequestParam("categoryId") int categoryId) {
		return this.productResource.getProductsByCategories(categoryId);
	}
	
	@GetMapping(value = "image/{productImageName}", produces = "image/*")
	public void fetchProductImage(@PathVariable("productImageName") String productImageName, HttpServletResponse resp) {
		this.productResource.fetchProductImage(productImageName, resp);
	}

	
	
	
}
