package com.example.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.model.Cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class CartResponse extends CommonApiResponse {
	
	private CartData cartData;
	private boolean activeCart;


}
