package com.example.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserOrderResponse extends CommonApiResponse{
	
	List<MyOrderResponse> orders = new ArrayList<>();


}
