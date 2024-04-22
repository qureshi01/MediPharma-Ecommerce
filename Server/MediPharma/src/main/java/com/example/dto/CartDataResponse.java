package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class CartDataResponse {
	
	private int cartId;
	
	private int userId;

	private int productId;

	private String productName;

	private String productDescription;

	private String productImage;

	private int quantity;

}
