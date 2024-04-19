package com.example.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.dao.CartDao;
import com.example.dto.AddToCartRequest;
import com.example.dto.CartDataResponse;
import com.example.dto.CartResponse;
import com.example.dto.CommonApiResponse;
import com.example.exception.CartSaveFailedException;
import com.example.model.Cart;
import com.example.model.Product;
import com.example.service.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component

public class CartResource {
	
	
	@Autowired
	private CartDao cartDao;
	
	@Autowired
	private ProductServiceImpl productService;
	
	
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
	
	public ResponseEntity<CartResponse> getCartDetailsByUserId(int userId) {
        CartResponse response = new CartResponse();
        List<CartDataResponse> cartDatas = new ArrayList<>();
        double totalCartPrice = 0;

        // Retrieve cart details by user ID
        List<Cart> userCarts = cartDao.findByUserId(userId);
        if (userCarts == null || userCarts.isEmpty()) {
            response.setResponseMessage("Cart is empty for the user");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Iterate through cart items
        for (Cart cart : userCarts) {
            // Retrieve product details using product ID
            Product product = productService.getProductById(cart.getProductId());

            // Check if product exists
            if (product == null) {
                response.setResponseMessage("Product not found for cart item");
                response.setSuccess(false);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // Perform calculations
            double productPrice =(product.getPrice());
            totalCartPrice += cart.getQuantity() * productPrice;

            // Create cart data response
            CartDataResponse cartData = new CartDataResponse();
            cartData.setCartId(cart.getId());
            cartData.setProductDescription(product.getDescription());
            cartData.setProductName(product.getTitle());
            cartData.setProductImage(product.getImageName());
            cartData.setQuantity(cart.getQuantity());
            cartData.setProductId(product.getId());
            cartDatas.add(cartData);
        }

        // Set calculated total cart price
        response.setTotalCartPrice(totalCartPrice);
        response.setCartData(cartDatas);
        response.setSuccess(true);
        response.setResponseMessage("Fetched Cart Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


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
