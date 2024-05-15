package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class UpdateDeliveryStatusRequest {
	
	private String orderId;
	
	private String deliveryStatus;
	
	private String deliveryTime;
	
	private String deliveryDate;
	

}
