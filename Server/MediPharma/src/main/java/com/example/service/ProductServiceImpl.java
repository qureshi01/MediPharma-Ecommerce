package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.dao.ProductDao;
import com.example.model.Product;
import com.example.utility.StorageService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired 
	private ProductDao productDao;

	@Override
	public void addProduct(Product product, String productImage) {
		String productImageName = productImage;
		
		product.setImageName(productImageName);
		
		this.productDao.save(product);
	}

}
