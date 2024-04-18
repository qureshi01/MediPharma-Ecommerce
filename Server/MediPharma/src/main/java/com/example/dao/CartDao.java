package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.example.model.Cart;

@Repository
public interface CartDao extends JpaRepository<Cart, Integer> {

	ResponseEntity<List<Cart>> findByUserId(int userId);
	

}
