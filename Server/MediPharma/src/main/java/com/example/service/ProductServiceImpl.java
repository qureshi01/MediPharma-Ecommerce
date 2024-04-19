package com.example.service;

import java.util.Optional;

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
	
	@Autowired
    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }
	
	@Autowired
	private StorageService storageService;

	@Override
	public void addProduct(Product product, MultipartFile productImage) {
		
		String productImageName = storageService.store(productImage);
		
		product.setImageName(productImageName);
		
		this.productDao.save(product);
	}

	@Override
	public Product getProductById(int productId) {
		Optional<Product> productOptional = productDao.findById(productId);
        return productOptional.orElse(null);
	}


}
