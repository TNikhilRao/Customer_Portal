package com.portal.util;

/**
 * @author Nikhil.Tirmanwar
 *
 */
public enum ResultCode {

	SUCCESS("0000", "success", ""), 
	SUCCESS_GET_CUSTOMER("0000", "success","Customer Details fetched successfully"), 
	SUCCESS_ADD_CUSTOMER("0000", "success",	"Customer registered successfully"), 
	SUCCESS_DELETE_CUSTOMER("0000", "success","Customer Details deleted successfully"),
	SUCCESS_UPDATE_CUSTOMER("0000", "success","Customer Details updated successfully"), 
	FAILURE_ADD_CUSTOMER("E0013", "failure","Failed to add customer, Email And MobileNumber must be Unique"), 
	FAILURE_GET_CUSTOMER("E0014", "failure","Failed to retrieve Customer Details"), 
	FAILURE_DELETE_CUSTOMER("0000", "failure","Customer Details failed to delete"), 
	FAILURE_UPDATE_CUSTOMER("0000", "failure","Customer Details failed to update"),
	FAILURE("E0011", "failure", ""),
	INVALID_CUSTOMER_PROFILE("400.100", "failure","Customer Profile Not found for Given MobileNumber"), 
	INVALID_MOBILE_NUMBER("400.100", "failure",	"Invalid MobileNumber");

	private String code;
	private String status;
	private String message;

	private ResultCode(String code, String status, String message) {
		this.code = code;
		this.status = status;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getStatus() {
		return status;
	}

}
