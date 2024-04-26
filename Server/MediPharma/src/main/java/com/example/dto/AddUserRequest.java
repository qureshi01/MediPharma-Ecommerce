package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class AddUserRequest {
	

	private String firstName;

	private String lastName;

	private String emailId;

	private String password;
	
	private String phoneNo;

	private String street;

	private String city;

	private int pincode;


	




	public static boolean validate(AddUserRequest request) {

		if (request.getFirstName() == null || request.getLastName() == null || request.getEmailId() == null
				|| request.getPassword() == null || request.getPhoneNo() == null || request.getStreet() == null
				|| request.getCity() == null ) {
			return false;
		}
		// Additional validation logic if needed

		return true;

	}

	
	
	

}
