package com.example.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dao.CartDao;
import com.example.dto.AddToCartRequest;
import com.example.dto.CartDataResponse;
import com.example.dto.CartResponse;
import com.example.dto.CommonApiResponse;
import com.example.dto.ProductResponse;
import com.example.dto.UserResponse;
import com.example.exception.CartSaveFailedException;
import com.example.model.Cart;
import com.example.model.Product;
import com.example.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component

public class CartResource {
	
	@Autowired
	private CartDao cartDao;
	
	@Autowired
	private ProductResource productService;
	
	@Autowired
	private UserResource userService;
	
	
	ObjectMapper objectMapper = new ObjectMapper();

	public ResponseEntity<CommonApiResponse> add(AddToCartRequest addToCartRequest) {
		CommonApiResponse response = new CommonApiResponse();

		if (addToCartRequest == null) {
			response.setResponseMessage("bad request - missing request");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (!AddToCartRequest.validateAddToCartRequest(addToCartRequest)) {
			response.setResponseMessage("bad request - missing field");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Cart cart = new Cart();
		cart.setProductId(addToCartRequest.getProductId());
		cart.setQuantity(addToCartRequest.getQuantity());
		cart.setUserId(addToCartRequest.getUserId());

		Cart addedCart = cartDao.save(cart);

		if (addedCart == null) {
			throw new CartSaveFailedException("Failed to Save the Cart");
		}

		response.setResponseMessage("Cart added successful!!!");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
	}

//	public ResponseEntity<CartResponse> fetchCart(int userId) {
//        CartResponse response = new CartResponse();
//
//        // Fetch user information
//        ResponseEntity<UserResponse> userResponseEntity = userService.fetchUserById(userId);
//        if (!userResponseEntity.getStatusCode().is2xxSuccessful() || userResponseEntity.getBody() == null) {
//            response.setResponseMessage("User service is down or user not found!!!");
//            response.setSuccess(false);
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }
//        User user = userResponseEntity.getBody().getUsers().get(0);
//
//        // Fetch cart information for the user
//        ResponseEntity<List<Cart>> cartResponseEntity = cartDao.findByUserId(userId);
//        if (!cartResponseEntity.getStatusCode().is2xxSuccessful() || cartResponseEntity.getBody() == null) {
//            response.setResponseMessage("Cart service is down or cart not found!!!");
//            response.setSuccess(false);
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }
//        List<Cart> carts = cartResponseEntity.getBody();
//
//        List<CartDataResponse> cartDatas = new ArrayList<>();
//        double totalCartPrice = 0.0;
//
//        for (Cart cart : carts) {
//            // Fetch product information for each cart item
//            ResponseEntity<ProductResponse> productResponseEntity = productService.getProductById(cart.getProductId());
//            if (!productResponseEntity.getStatusCode().is2xxSuccessful() || productResponseEntity.getBody() == null) {
//                response.setResponseMessage("Product service is down or product not found!!!");
//                response.setSuccess(false);
//                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//            }
//            Product product = productResponseEntity.getBody().getProducts().get(0);
//
//            // Create CartDataResponse object with information from cart and product
//            CartDataResponse cartData = new CartDataResponse();
//            cartData.setCartId(cart.getId());
//            cartData.setProductId(product.getId());
//            cartData.setProductName(product.getTitle());
//            cartData.setProductDescription(product.getDescription());
//            cartData.setProductImage(product.getImageName());
//            cartData.setQuantity(cart.getQuantity());
//
//            // Calculate total price for the cart
//            double productPrice = Double.parseDouble(product.getPrice().toString());
//            totalCartPrice += cart.getQuantity() * productPrice;
//
//            // Add cartData to the list
//            cartDatas.add(cartData);
//        }
//
//        // Set response fields
//        response.setTotalCartPrice(String.valueOf(totalCartPrice));
//        response.setCartData(cartDatas);
//        response.setResponseMessage("User cart fetched successfully!!!");
//        response.setSuccess(true);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }


	public ResponseEntity<CommonApiResponse> removeCartItem(int cartId) {
		CommonApiResponse response = new CommonApiResponse();

		if (cartId == 0) {
			response.setResponseMessage("bad request - missing input");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Optional<Cart> optionalCart = this.cartDao.findById(cartId);
		Cart cart = new Cart();

		if (optionalCart.isPresent()) {
			cart = optionalCart.get();
		}

		if (cart == null) {
			response.setResponseMessage("Cart not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			this.cartDao.delete(cart);
		} catch (Exception e) {
			response.setResponseMessage("Failed to delete Cart!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.setResponseMessage("product deleted from Cart Successfull!!!");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
	}

}
