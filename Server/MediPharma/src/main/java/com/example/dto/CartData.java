package com.example.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartData {
	
	private double totalCartPrice;
    private List<CartDataResponse> cartProducts;

}
