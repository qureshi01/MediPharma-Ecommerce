package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.ProductResponse;
import com.example.model.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {

	List<Product> findByCategoryId(int category);
	
}
