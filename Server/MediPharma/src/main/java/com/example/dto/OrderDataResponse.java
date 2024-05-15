package com.example.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class OrderDataResponse extends CommonApiResponse{
	
	private List<MyOrderResponse> orderResponse;
	
	private String totalCartPrice;

}
