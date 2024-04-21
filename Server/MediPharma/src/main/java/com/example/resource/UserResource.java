package com.example.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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

		if (userRequest.getPhoneNo().length() != 10) {
			response.setResponseMessage("Enter Valid Mobile No");
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
		
		String encodedPassword = userRequest.getPassword();

		User user = new User();
		user.setAddress(addAddress);
		user.setEmailId(userRequest.getEmailId());
		user.setFirstName(userRequest.getFirstName());
		user.setLastName(userRequest.getLastName());
		user.setPhoneNo(userRequest.getPhoneNo());
		user.setPassword(encodedPassword);
		user.setRole(userRequest.getRole());
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
	
	public ResponseEntity<UserResponse> getAllDeliveryPersons() {
		UserResponse response = new UserResponse();

		List<User> deliveryPersons = this.userDao.findByRole("Delivery");

		if (CollectionUtils.isEmpty(deliveryPersons)) {
			response.setResponseMessage("No Delivery Person Found");
			response.setSuccess(false);

			return new ResponseEntity<UserResponse>(response, HttpStatus.OK);
		}

		response.setUsers(deliveryPersons);
		response.setResponseMessage("Delivery Persons Fected Success!!!");
		response.setSuccess(true);

		return new ResponseEntity<UserResponse>(response, HttpStatus.OK);
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