package com.example.resource;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.dao.AddressDao;
import com.example.dao.UserDao;
import com.example.dto.AddUserRequest;
import com.example.dto.UserLoginRequest;
import com.example.dto.UserResponse;
import com.example.exception.UserSaveFailedException;
import com.example.model.Address;
import com.example.model.User;

import jakarta.transaction.Transactional;

@Component
@Transactional
public class UserResource {

	@Autowired
	private UserDao userDao;

	@Autowired
	private AddressDao addressDao;
	

	public ResponseEntity<UserResponse> registerUser(AddUserRequest userRequest) {
		UserResponse response = new UserResponse();

		if (userRequest == null) {
			response.setResponseMessage("bad request - missing request");
			response.setSuccess(false);

			return new ResponseEntity<UserResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (!AddUserRequest.validate(userRequest)) {
			response.setResponseMessage("bad request - missing input");
			response.setSuccess(false);

			return new ResponseEntity<UserResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Address address = new Address();
		address.setCity(userRequest.getCity());
		address.setPincode(userRequest.getPincode());
		address.setStreet(userRequest.getStreet());

		Address addAddress = addressDao.save(address);

		if (addAddress == null) {
			response.setResponseMessage("Failed to register User");
			response.setSuccess(false);

			return new ResponseEntity<UserResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		String Password =userRequest.getPassword();

		User user = new User();
		user.setAddress(addAddress);
		user.setEmailId(userRequest.getEmailId());
		user.setUname(userRequest.getUname());
		user.setPassword(Password);
		User addUser = userDao.save(user);

		if (addUser == null) {
			throw new UserSaveFailedException("Failed to register the User");
		}

		response.setUsers(Arrays.asList(addUser));
		response.setResponseMessage("User Registered Successful");
		response.setSuccess(true);

		return new ResponseEntity<UserResponse>(response, HttpStatus.OK);
	}

//	public ResponseEntity<UserResponse> loginUser(UserLoginRequest loginRequest) {
//		UserResponse response = new UserResponse();
//
//		if (loginRequest == null) {
//			response.setResponseMessage("bad request - missing request");
//			response.setSuccess(false);
//
//			return new ResponseEntity<UserResponse>(response, HttpStatus.BAD_REQUEST);
//		}
//
//		if (!UserLoginRequest.validateLoginRequest(loginRequest)) {
//			response.setResponseMessage("bad request - missing input");
//			response.setSuccess(false);
//
//			return new ResponseEntity<UserResponse>(response, HttpStatus.BAD_REQUEST);
//		}
//		
//		// Implementation of login logic goes here
//        
//        return new ResponseEntity<UserResponse>(response, HttpStatus.OK);
//		
//	}

	public ResponseEntity<UserResponse> fetchUserById(Integer userId) {
		UserResponse userResponse = new UserResponse();

		if (userId == null) {
			userResponse.setResponseMessage("User Id is missing!!");
			userResponse.setSuccess(false);

			return new ResponseEntity<UserResponse>(userResponse, HttpStatus.BAD_REQUEST);
		}

		Optional<User> optional = this.userDao.findById(userId);

		if (optional.isEmpty()) {
			userResponse.setResponseMessage("User not found!!!");
			userResponse.setSuccess(false);

			return new ResponseEntity<UserResponse>(userResponse, HttpStatus.BAD_REQUEST);
		}

		User user = optional.get();

		userResponse.setUsers(Arrays.asList(user));
		userResponse.setResponseMessage("User Fetched Successful!!!");
		userResponse.setSuccess(true);

		return new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);
	}

}