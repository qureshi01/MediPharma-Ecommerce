package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.CartDao;
import com.example.dto.CommonApiResponse;
import com.example.model.Cart;

@Service
public class CartService {
    
    private final CartDao cartRepository;

   
    public CartService(CartDao cartRepository) {
        this.cartRepository = cartRepository;
    }

    public CommonApiResponse deleteById(int cartId) {
        CommonApiResponse response = new CommonApiResponse();
        
        try {
            // Check if the cart item exists
            Optional<Cart> optionalCart = cartRepository.findById(cartId);
            if (optionalCart.isPresent()) {
                // If the cart item exists, delete it
                cartRepository.deleteById(cartId);
                response.setResponseMessage("Cart item deleted successfully");
                response.setSuccess(true);
            } else {
                // If the cart item does not exist, return a failure response
                response.setResponseMessage("Cart item not found");
                response.setSuccess(false);
            }
        } catch (Exception e) {
            // Handle any exceptions that occur during the deletion process
            response.setResponseMessage("Failed to delete cart item: " + e.getMessage());
            response.setSuccess(false);
        }
        
        return response;
    }
}
