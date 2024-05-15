package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddToCartRequest {
	
	private int productId;
	
	private int quantity;
	
	private int userId;
	
	public static boolean validateAddToCartRequest(AddToCartRequest request) {
        if (request.getProductId() <= 0 || request.getQuantity() <= 0 || request.getUserId() <= 0) {
            return false;
        }
        
        return true;
    }

}
