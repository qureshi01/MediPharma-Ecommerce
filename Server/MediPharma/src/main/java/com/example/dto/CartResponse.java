package com.example.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class CartResponse extends CommonApiResponse {
	
	private double totalCartPrice;
	
	private List<CartDataResponse> cartData = new ArrayList();

}
