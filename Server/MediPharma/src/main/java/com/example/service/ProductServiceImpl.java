package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.ProductDao;
import com.example.model.Product;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired 
	private ProductDao productDao;
	
    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }
	


	@Override
	public Product getProductById(int productId) {
		Optional<Product> productOptional = productDao.findById(productId);
        return productOptional.orElse(null);
	}



	@Override
	public void addProduct(Product product) {
		// TODO Auto-generated method stub
		this.productDao.save(product);
		
	}



}
