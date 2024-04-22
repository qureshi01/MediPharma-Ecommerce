package com.example.dto;

import com.example.model.Address;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MyOrderResponse {
	
	private String orderId;
	
    private int productId;
    
    private int userId;
    
    private String userName;  // first name + last name
    
    private Address address;
    
    private String userPhone;
	
	private String productName;
	
	private String productDescription;
	
	private String productImage;
	
	private int quantity;
	
	private double totalPrice;
	
	private String orderDate;
	
	private String deliveryDate;
	
	private String deliveryStatus;
	

}
