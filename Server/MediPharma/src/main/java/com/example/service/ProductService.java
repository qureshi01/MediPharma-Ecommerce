package com.example.service;

import org.springframework.web.multipart.MultipartFile;

import com.example.dto.ProductResponse;
import com.example.model.Product;

public interface ProductService {
	
	void addProduct(Product product, MultipartFile productImage);
	public Product getProductById(int productId);
	
	//ProductResponse getProductId(int  productId);
	

}
