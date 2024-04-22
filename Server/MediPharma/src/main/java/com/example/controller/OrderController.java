package com.example.controller;

import javax.management.ServiceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CommonApiResponse;
import com.example.dto.UserOrderResponse;
import com.example.resource.OrderResource;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("api/order/")
public class OrderController {

	@Autowired
	private OrderResource orderResource;

	@PostMapping("add")
	public ResponseEntity<CommonApiResponse> customerOrder(@RequestParam("userId") int userId)
			throws JsonProcessingException, ServiceNotFoundException {
		return this.orderResource.customerOrder(userId);
	}
	
	@GetMapping("myorder")
	public ResponseEntity<UserOrderResponse> getMyOrder(@RequestParam("userId") int userId)
			throws JsonProcessingException {
		return this.orderResource.getMyOrder(userId);
	}
	
	@GetMapping("admin/allorder")
	public ResponseEntity<UserOrderResponse> getAllOrder() throws JsonProcessingException {
		return this.orderResource.getAllOrder();
	}

	@GetMapping("admin/showorder")
	public ResponseEntity<UserOrderResponse> getOrdersByOrderId(@RequestParam("orderId") String orderId)
			throws JsonProcessingException {
		return this.orderResource.getOrdersByOrderId(orderId);
	}

}
