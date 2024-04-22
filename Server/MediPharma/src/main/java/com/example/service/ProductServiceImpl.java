package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.dao.ProductDao;
import com.example.dto.ProductResponse;
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

//	@Override
//	public ProductResponse getProductId(int productId) {
//        Optional<Product> optionalProduct = productDao.findById(productId);
//        
//        if (optionalProduct.isPresent()) {
//            Product product = optionalProduct.get();
//            // Convert Product entity to ProductResponse
//            ProductResponse productResponse = new ProductResponse();
//            // Set product details in the productResponse object
//            productResponse.setId(product.getId());
//            productResponse.setTitle(product.getTitle());
//            productResponse.setDescription(product.getDescription());
//            productResponse.setPrice(product.getPrice());
//            productResponse.setQuantity(product.getQuantity());
//            productResponse.setImageName(product.getImageName());
//            // You can add more fields as needed
//            
//            return productResponse;
//        } else {
//            // Handle case where product with given ID is not found
//            return null; // Or throw an exception if required
//        }
//    }


}
