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
import com.example.dto.CartData;
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

	
	public ResponseEntity<CommonApiResponse> incrementQuantity( AddToCartRequest cartRequest) {
	    CommonApiResponse response = new CommonApiResponse();

	    try {
	        // Validate the request
	        if (cartRequest == null) {
	            response.setResponseMessage("Bad request - missing request parameters");
	            response.setSuccess(false);
	            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	        }

	        // Retrieve the cart entry for the given product and user
	        Cart cart = cartDao.findByProductIdAndUserId(cartRequest.getProductId(), cartRequest.getUserId());

	        if (cart != null) {
	            // Increment the quantity by one
	            cart.setQuantity(cart.getQuantity() + 1);
	            cartDao.save(cart);
	        } else {
	            // If cart entry does not exist, create a new one with quantity set to one
	            cart = new Cart();
	            cart.setProductId(cartRequest.getProductId());
	            cart.setUserId(cartRequest.getUserId());
	            cart.setQuantity(1);
	            cartDao.save(cart);
	        }

	        response.setResponseMessage("Quantity incremented successfully");
	        response.setSuccess(true);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	        response.setResponseMessage("Internal server error: " + e.getMessage());
	        response.setSuccess(false);
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	public ResponseEntity<CommonApiResponse> decrementQuantity( AddToCartRequest cartRequest) {
	    CommonApiResponse response = new CommonApiResponse();

	    try {
	        // Validate the request
	        if (cartRequest == null) {
	            response.setResponseMessage("Bad request - missing request parameters");
	            response.setSuccess(false);
	            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	        }

	        // Retrieve the cart entry for the given product and user
	        Cart cart = cartDao.findByProductIdAndUserId(cartRequest.getProductId(), cartRequest.getUserId());

	        if (cart != null) {
	            // Decrement the quantity by one if it's greater than 0
	            if (cart.getQuantity() > 0) {
	                cart.setQuantity(cart.getQuantity() - 1);
	                cartDao.save(cart);
	                response.setResponseMessage("Quantity decremented successfully");
	                response.setSuccess(true);
	            } else {
	                response.setResponseMessage("Quantity already at minimum (0)");
	                response.setSuccess(false);
	            }
	        } else {
	            // If cart entry does not exist, return with an error message
	            response.setResponseMessage("Cart entry not found");
	            response.setSuccess(false);
	        }

	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	        response.setResponseMessage("Internal server error: " + e.getMessage());
	        response.setSuccess(false);
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	 public ResponseEntity<CartResponse> getCartDetailsByUserId(int userId) {
		    CartResponse response = new CartResponse();
		    CartData cartData = new CartData();
		    List<CartDataResponse> cartProducts = new ArrayList<>();
		    double totalCartPrice = 0;

		    // Retrieve cart details by user ID
		    List<Cart> userCarts = cartDao.findByUserId(userId);
		    if (userCarts == null || userCarts.isEmpty()) {
		        response.setResponseMessage("Cart is empty for the user");
		        response.setSuccess(false);
		        cartData.setCartProducts(cartProducts); // Set empty list for cartProducts
		        cartData.setTotalCartPrice(totalCartPrice); // Set totalCartPrice to 0
		        response.setCartData(cartData);
		      
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
		        double productPrice = product.getPrice()-product.getDisc_price();
		        totalCartPrice += cart.getQuantity() * productPrice;

		        // Create cart data response
		        CartDataResponse cartDataItem = new CartDataResponse();
		        cartDataItem.setCartId(cart.getId());
		        cartDataItem.setUserId(cart.getUserId());
		        cartDataItem.setProductDescription(product.getDescription());
		        cartDataItem.setProductName(product.getTitle());
		        cartDataItem.setProductImage(product.getImage());
		        cartDataItem.setQuantity(cart.getQuantity());
		        cartDataItem.setProductId(product.getId());
		        cartDataItem.setPrice(product.getPrice()-product.getDisc_price());
		        cartDataItem.setTotalPrice((product.getPrice()-product.getDisc_price()) * cart.getQuantity());
		        cartProducts.add(cartDataItem);
		    }

		    // Set calculated total cart price and cart products to the CartData object
		    cartData.setTotalCartPrice(totalCartPrice);
		    cartData.setCartProducts(cartProducts);

		    // Set CartData object to the response
		    response.setCartData(cartData);
		    response.setSuccess(true);
		    response.setActiveCart(true);
		    response.setResponseMessage("Fetched Cart Successfully");
		    return new ResponseEntity<>(response, HttpStatus.OK);
		}

	public ResponseEntity<CommonApiResponse> deleteCartItem(int cartId) {
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
