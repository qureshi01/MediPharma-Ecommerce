package com.example.resource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.management.ServiceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.example.dao.CartDao;
import com.example.dao.OrderDao;
import com.example.dao.UserDao;
import com.example.dto.CartData;
import com.example.dto.CartResponse;
import com.example.dto.CommonApiResponse;
import com.example.dto.MyOrderResponse;
import com.example.dto.UpdateDeliveryStatusRequest;
import com.example.dto.UserOrderResponse;
import com.example.exception.OrderSaveFailedException;
import com.example.model.Cart;
import com.example.model.Orders;
import com.example.model.Product;
import com.example.model.User;
import com.example.service.CartService;
import com.example.service.ProductServiceImpl;
import com.example.utility.Constants.DeliveryStatus;
import com.example.utility.Constants.DeliveryTime;
import com.example.utility.Helper;

import jakarta.transaction.Transactional;


@Component
@Transactional
public class OrderResource {
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private UserDao userService;

	@Autowired
	private CartDao cartService;
	
	@Autowired
	private CartService cartDao;

	@Autowired
	private ProductServiceImpl productService;
	
	public ResponseEntity<CommonApiResponse> customerOrder(int userId) {
        CommonApiResponse response = new CommonApiResponse();

        if (userId == 0) {
            response.setResponseMessage("bad request - missing field");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        String orderId = Helper.getAlphaNumericOrderId();

        // Assuming the cartService returns a List<Cart>
        List<Cart> carts = this.cartService.getCartByUserId(userId);

        if (carts == null || carts.isEmpty()) {
            response.setResponseMessage("Your Cart is Empty!!!");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formatDateTime = currentDateTime.format(formatter);

        try {
            for (Cart cart : carts) {
                Orders order = new Orders();
                order.setOrderId(orderId);
                order.setUserId(userId);
                order.setProductId(cart.getProductId());
                order.setQuantity(cart.getQuantity());
                order.setOrderDate(formatDateTime);
                order.setDeliveryDate(DeliveryStatus.PENDING.value());
                order.setDeliveryStatus(DeliveryStatus.PENDING.value());
                order.setDeliveryTime(DeliveryTime.DEFAULT.value());

                Orders savedOrder = orderDao.save(order);

                if (savedOrder == null) {
                    throw new OrderSaveFailedException("Failed to save the Order");
                }
                
                CommonApiResponse cartRemoveResponse = cartDao.deleteById(cart.getId());
                if (cartRemoveResponse != null && cartRemoveResponse.isSuccess()) {
                    // Cart item removed successfully
                } else {
                    // Handle the failure to remove item from the cart
                    response.setResponseMessage("Failed to remove item from the cart");
                    response.setSuccess(false);
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            response.setResponseMessage("Order placed successfully");
            response.setSuccess(true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setResponseMessage("Failed to Order Products: " + e.getMessage());
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	
	public ResponseEntity<UserOrderResponse> getMyOrder(int userId) {
	    UserOrderResponse response = new UserOrderResponse();

	    if (userId == 0) {
	        response.setResponseMessage("User Id missing");
	        response.setSuccess(false);
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    List<Orders> userOrders = orderDao.findByUserId(userId);

	    if (userOrders.isEmpty()) {
	        response.setResponseMessage("Orders not found for user id: " + userId);
	        response.setSuccess(false);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    List<MyOrderResponse> orderDatas = new ArrayList<>();

	    for (Orders order : userOrders) {
	        Product productResponse = productService.getProductById(order.getProductId());

	        if (productResponse == null) {
	            response.setResponseMessage("Product not found for order id: " + order.getOrderId());
	            response.setSuccess(false);
	            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	        }

	        Product product = productResponse;
	        User user = null;
	        
	        User userResponse = this.userService.getUserById(order.getUserId());
	        
	        user = userResponse;
	        
	        MyOrderResponse orderData = new MyOrderResponse();
	        orderData.setUserId(order.getUserId());
	        orderData.setOrderId(order.getOrderId());
	        orderData.setUserName(user.getFirstName() + " " + user.getLastName());
	        orderData.setUserPhone(user.getPhoneNo());
			orderData.setAddress(user.getAddress());
	        orderData.setProductDescription(product.getDescription());
	        orderData.setProductName(product.getTitle());
	        orderData.setProductImage(product.getImage());
	        orderData.setQuantity(order.getQuantity());
	        orderData.setOrderDate(order.getOrderDate());
	        orderData.setProductId(product.getId());
	        orderData.setDeliveryDate(order.getDeliveryDate() + " " + order.getDeliveryTime());
	        orderData.setDeliveryStatus(order.getDeliveryStatus());
	        orderData.setTotalPrice(order.getQuantity() * product.getPrice());
	        
	        orderDatas.add(orderData);
	    }

	    response.setOrders(orderDatas);
	    response.setResponseMessage("Orders fetched successfully for user id: " + userId);
	    response.setSuccess(true);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}


	public ResponseEntity<UserOrderResponse> getAllOrder() {
		UserOrderResponse response = new UserOrderResponse();

		List<Orders> userOrder = orderDao.findAll();

		if (CollectionUtils.isEmpty(userOrder)) {
			response.setResponseMessage("Orders not found");
			response.setSuccess(false);

			return new ResponseEntity<UserOrderResponse>(response, HttpStatus.OK);
		}

		List<MyOrderResponse> orderDatas = new ArrayList<>();

		for (Orders order : userOrder) {

			Product productResponse = productService.getProductById(order.getProductId());

			if (productResponse == null) {
				response.setResponseMessage("product service is down!!!");
				response.setSuccess(false);

				return new ResponseEntity<UserOrderResponse>(response, HttpStatus.BAD_REQUEST);
			}

			Product product = productResponse;

			
			User user = null;

			User userResponse = this.userService.getUserById(order.getUserId());

			if (userResponse == null) {
				response.setResponseMessage("user service is down!!!");
				response.setSuccess(false);

				return new ResponseEntity<UserOrderResponse>(response, HttpStatus.BAD_REQUEST);
			}

			user = userResponse;

			MyOrderResponse orderData = new MyOrderResponse();
			orderData.setOrderId(order.getOrderId());
			orderData.setProductDescription(product.getDescription());
			orderData.setProductName(product.getTitle());
			orderData.setProductImage(product.getImage());
			orderData.setQuantity(order.getQuantity());
			orderData.setOrderDate(order.getOrderDate());
			orderData.setProductId(product.getId());
			orderData.setDeliveryDate(order.getDeliveryDate() + " " + order.getDeliveryTime());
			orderData.setDeliveryStatus(order.getDeliveryStatus());
			orderData.setTotalPrice(order.getQuantity() * product.getPrice());
			orderData.setUserId(user.getId());
			orderData.setUserName(user.getFirstName() + " " + user.getLastName());
			orderData.setUserPhone(user.getPhoneNo());
			orderData.setAddress(user.getAddress());
			orderDatas.add(orderData);

		}

		response.setOrders(orderDatas);
		response.setResponseMessage("Order Fetched Successful!!");
		response.setSuccess(true);

		return new ResponseEntity<UserOrderResponse>(response, HttpStatus.OK);
	}
	
	public ResponseEntity<UserOrderResponse> getOrdersByOrderId(String orderId) {
		UserOrderResponse response = new UserOrderResponse();

		if (orderId == null) {
			response.setResponseMessage("Orders not found");
			response.setSuccess(false);

			return new ResponseEntity<UserOrderResponse>(response, HttpStatus.OK);
		}

		List<Orders> userOrder = orderDao.findByOrderId(orderId);

		if (CollectionUtils.isEmpty(userOrder)) {
			response.setResponseMessage("Orders not found");
			response.setSuccess(false);

			return new ResponseEntity<UserOrderResponse>(response, HttpStatus.OK);
		}

		List<MyOrderResponse> orderDatas = new ArrayList<>();

		for (Orders order : userOrder) {

			Product productResponse = productService.getProductById(order.getProductId());

			if (productResponse == null) {
				response.setResponseMessage("product service is down!!!");
				response.setSuccess(false);

				return new ResponseEntity<UserOrderResponse>(response, HttpStatus.BAD_REQUEST);
			}

			Product product = productResponse;

			User user = null;

			User userResponse = this.userService.getUserById(order.getUserId());

			if (userResponse == null) {
				response.setResponseMessage("user service is down!!!");
				response.setSuccess(false);

				return new ResponseEntity<UserOrderResponse>(response, HttpStatus.BAD_REQUEST);
			}

			user = userResponse;

			MyOrderResponse orderData = new MyOrderResponse();
			orderData.setOrderId(order.getOrderId());
			orderData.setProductDescription(product.getDescription());
			orderData.setProductName(product.getTitle());
			orderData.setProductImage(product.getImage());
			orderData.setQuantity(order.getQuantity());
			orderData.setOrderDate(order.getOrderDate());
			orderData.setProductId(product.getId());
			orderData.setDeliveryDate(order.getDeliveryDate() + " " + order.getDeliveryTime());
			orderData.setDeliveryStatus(order.getDeliveryStatus());
			orderData.setTotalPrice(order.getQuantity() * product.getPrice());
			orderData.setUserId(user.getId());
			orderData.setUserName(user.getFirstName() + " " + user.getLastName());
			orderData.setUserPhone(user.getPhoneNo());
			orderData.setAddress(user.getAddress());
			orderDatas.add(orderData);

		}

		response.setOrders(orderDatas);
		response.setResponseMessage("Order Fetched Successful!!");
		response.setSuccess(true);

		return new ResponseEntity<UserOrderResponse>(response, HttpStatus.OK);
	}
	
	public ResponseEntity<UserOrderResponse> updateOrderDeliveryStatus(UpdateDeliveryStatusRequest deliveryRequest) {
		UserOrderResponse response = new UserOrderResponse();

		if (deliveryRequest == null) {
			response.setResponseMessage("bad request - missing request");
			response.setSuccess(false);

			return new ResponseEntity<UserOrderResponse>(response, HttpStatus.BAD_REQUEST);
		}

		List<Orders> orders = orderDao.findByOrderId(deliveryRequest.getOrderId());

		if (CollectionUtils.isEmpty(orders)) {
			response.setResponseMessage("Orders not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<UserOrderResponse>(response, HttpStatus.BAD_REQUEST);
		}

		for (Orders order : orders) {
			order.setDeliveryDate(deliveryRequest.getDeliveryDate());
			order.setDeliveryStatus(deliveryRequest.getDeliveryStatus());
			order.setDeliveryTime(deliveryRequest.getDeliveryTime());
			orderDao.save(order);
		}

		List<Orders> userOrder = orderDao.findByOrderId(deliveryRequest.getOrderId());

		List<MyOrderResponse> orderDatas = new ArrayList<>();

		for (Orders order : userOrder) {

			Product productResponse = productService.getProductById(order.getProductId());

			if (productResponse == null) {
				response.setResponseMessage("product service is down!!!");
				response.setSuccess(false);

				return new ResponseEntity<UserOrderResponse>(response, HttpStatus.BAD_REQUEST);
			}

			Product product = productResponse;

			User user = null;

			User userResponse = this.userService.getUserById(order.getUserId());

			if (userResponse == null) {
				response.setResponseMessage("user service is down!!!");
				response.setSuccess(false);

				return new ResponseEntity<UserOrderResponse>(response, HttpStatus.BAD_REQUEST);
			}

			user = userResponse;

			MyOrderResponse orderData = new MyOrderResponse();
			orderData.setOrderId(order.getOrderId());
			orderData.setProductDescription(product.getDescription());
			orderData.setProductName(product.getTitle());
			orderData.setProductImage(product.getImage());
			orderData.setQuantity(order.getQuantity());
			orderData.setOrderDate(order.getOrderDate());
			orderData.setProductId(product.getId());
			orderData.setDeliveryDate(order.getDeliveryDate() + " " + order.getDeliveryTime());
			orderData.setDeliveryStatus(order.getDeliveryStatus());
			orderData.setTotalPrice(order.getQuantity() * product.getPrice());
			orderData.setUserId(user.getId());
			orderData.setUserName(user.getFirstName() + " " + user.getLastName());
			orderData.setUserPhone(user.getPhoneNo());
			orderData.setAddress(user.getAddress());
			orderDatas.add(orderData);

		}

		response.setOrders(orderDatas);
		response.setResponseMessage("Order Fetched Successful!!");
		response.setSuccess(true);

		return new ResponseEntity<UserOrderResponse>(response, HttpStatus.OK);
	}
	
	
	

}
