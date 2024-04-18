package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.AddToCartRequest;
import com.example.dto.CartResponse;
import com.example.dto.CommonApiResponse;
import com.example.resource.CartResource;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("api/cart")
public class CartController {
	
	@Autowired
	private CartResource cartResource;

	@PostMapping("add")
	public ResponseEntity<CommonApiResponse> add(@RequestBody AddToCartRequest addToCartRequest) {
		return this.cartResource.add(addToCartRequest);
	}

//	@GetMapping("fetch")
//	public ResponseEntity<CartResponse> getMyCart(@RequestParam("userId") int userId) throws JsonProcessingException {
//		return this.cartResource.fetchCart(userId);
//	}

	@GetMapping("remove")
	public ResponseEntity<CommonApiResponse> removeCartItem(@RequestParam("cartId") int cartId)
			throws JsonProcessingException {

		return this.cartResource.removeCartItem(cartId);
	}

}
