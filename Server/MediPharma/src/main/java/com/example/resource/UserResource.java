package com.example.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.dao.AddressDao;
import com.example.dao.CartDao;
import com.example.dao.OrderDao;
import com.example.dao.UserDao;
import com.example.dto.AddUserRequest;
import com.example.dto.CartDataResponse;
import com.example.dto.OrderDataResponse;
import com.example.dto.UserLoginRequest;
import com.example.dto.UserResponse;
import com.example.exception.UserSaveFailedException;
import com.example.model.Address;
import com.example.model.Cart;
import com.example.model.Orders;
import com.example.model.User;

import jakarta.transaction.Transactional;

@Component
@Transactional
public class UserResource {

	@Autowired
	private UserDao userDao;

	@Autowired
	private AddressDao addressDao;
	
	
	@Autowired
    private JavaMailSender mailSender;
	
	
	public ResponseEntity<UserResponse> registerUser(AddUserRequest userRequest) {
	    UserResponse response = new UserResponse();

	    // Check if userRequest is null
	    if (userRequest == null) {
	        response.setResponseMessage("Bad request - missing request");
	        response.setSuccess(false);
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    // Check if required fields are missing
	    if (!AddUserRequest.validate(userRequest)) {
	        response.setResponseMessage("Bad request - missing input");
	        response.setSuccess(false);
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    // Check if phone number is valid
	    if (userRequest.getPhoneNo().length() != 10) {
	        response.setResponseMessage("Enter a valid mobile number");
	        response.setSuccess(false);
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    // Check if email ID already exists
	    if (userDao.existsByEmailId(userRequest.getEmailId())) {
	        response.setResponseMessage("Email ID already exists");
	        response.setSuccess(false);
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    // Save address
	    Address address = new Address();
	    address.setCity(userRequest.getCity());
	    address.setPincode(userRequest.getPincode());
	    address.setStreet(userRequest.getStreet());
	    Address savedAddress = addressDao.save(address);

	    // Check if address saved successfully
	    if (savedAddress == null) {
	        response.setResponseMessage("Failed to register user");
	        response.setSuccess(false);
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    // Encode password and save user
	    String encodedPassword = userRequest.getPassword();
	    User user = new User();
	    user.setAddress(savedAddress);
	    user.setEmailId(userRequest.getEmailId());
	    user.setFirstName(userRequest.getFirstName());
	    user.setLastName(userRequest.getLastName());
	    user.setPhoneNo(userRequest.getPhoneNo());
	    user.setPassword(encodedPassword);
	    user.setRole(userRequest.getRole());

	    try {
	        User savedUser = userDao.save(user);
	        response.setUsers(Arrays.asList(savedUser));
	        response.setRole(savedUser.getRole());
	        response.setFirstName(savedUser.getFirstName());
	        response.setUserId(savedUser.getId());
	        response.setResponseMessage("User registered successfully");
	        response.setSuccess(true);
	        
	        // Send registration success email
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setFrom("sender@gmail.com");
	        message.setTo(user.getEmailId());
	        message.setSubject("Registration Successful");
	        message.setText("User Registered Successfully");
	        mailSender.send(message);

	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (DataIntegrityViolationException e) {
	        // Catch if email ID constraint violation occurs
	        response.setResponseMessage("Failed to register user. Email ID already exists.");
	        response.setSuccess(false);
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }
	}
	

	public ResponseEntity<UserResponse> loginUser(UserLoginRequest loginRequest) {
	    UserResponse response = new UserResponse();

	    // Your existing login logic to authenticate the user...

	    User user = userDao.findByEmailId(loginRequest.getEmailId());
	    if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
	        response.setResponseMessage("Login successful");
	        response.setSuccess(true);
	        response.setRole(user.getRole()); // Set the user's role in the response
	        response.setFirstName(user.getFirstName());
	        response.setUserId(user.getId());
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } else {
	        response.setResponseMessage("Invalid username or password");
	        response.setSuccess(false);
	        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	    }
	}
   
	
	public ResponseEntity<UserResponse> fetchUserById(Integer userId) {
		UserResponse response = new UserResponse();

		if (userId == null) {
			response.setResponseMessage("User Id is missing!!");
			response.setSuccess(false);

			return new ResponseEntity<UserResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Optional<User> optional = this.userDao.findById(userId);

		if (optional.isEmpty()) {
			response.setResponseMessage("User not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<UserResponse>(response, HttpStatus.BAD_REQUEST);
		}

		User user = optional.get();

		response.setUsers(Arrays.asList(user));
		response.setResponseMessage("User Fetched Successful!!!");
		response.setSuccess(true);

		return new ResponseEntity<UserResponse>(response, HttpStatus.OK);
	}


}