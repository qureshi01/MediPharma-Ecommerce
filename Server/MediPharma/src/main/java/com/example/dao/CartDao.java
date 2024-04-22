package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.CommonApiResponse;
import com.example.model.Cart;

@Repository
public interface CartDao extends JpaRepository<Cart, Integer> {

 // Method to retrieve user carts by user ID
    List<Cart> findByUserId(int userId);
    
    List<Cart> getCartByUserId(int userId);
    
    //CommonApiResponse removeCartById(int cartId);

   


}


