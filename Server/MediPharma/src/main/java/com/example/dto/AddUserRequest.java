package com.example.dto;

public class AddUserRequest {
	

	private String uname;

	private String emailId;

	private String password;

	private String street;

	private String city;

	private int pincode;


	

	@Override
	public String toString() {
		return "AddUserRequest [uname=" + uname + ", emailId=" + emailId + ", password=" + password + ", street="
				+ street + ", city=" + city + ", pincode=" + pincode + "]";
	}




	public String getUname() {
		return uname;
	}




	public void setUname(String uname) {
		this.uname = uname;
	}




	public String getEmailId() {
		return emailId;
	}




	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}




	public String getPassword() {
		return password;
	}




	public void setPassword(String password) {
		this.password = password;
	}




	public String getStreet() {
		return street;
	}




	public void setStreet(String street) {
		this.street = street;
	}




	public String getCity() {
		return city;
	}




	public void setCity(String city) {
		this.city = city;
	}




	public int getPincode() {
		return pincode;
	}




	public void setPincode(int pincode) {
		this.pincode = pincode;
	}




	public static boolean validate(AddUserRequest request) {

		if (request.getUname() == null || request.getEmailId() == null
				|| request.getPassword() == null || request.getStreet() == null
				|| request.getCity() == null) {
			return false;
		}
		// Additional validation logic if needed

		return true;

	}

	
	
	

}
