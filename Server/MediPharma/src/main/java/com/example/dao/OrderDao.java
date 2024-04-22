package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.ProductResponse;
import com.example.model.Orders;

@Repository
public interface OrderDao extends JpaRepository<Orders, Integer> {
	
	List<Orders> findByOrderId(String orderId);
	List<Orders> findByUserIdAndProductId(int userId, int productId);
	List<Orders> findByUserId(int userId);
	
	

}
