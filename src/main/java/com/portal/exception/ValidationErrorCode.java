package com.portal.exception;

public enum ValidationErrorCode {

	MOBILE_NUMBER_MANDATORY("400.100", "MobileNumber is mandatory And It must contains only 10 digts"), 
	NAME_MANDATORY("400.200", "Name is mandatory and It must contains Only Chars and Size upto 20"), 
	EMAIL_INVALID("400.300","Invalid Email"), 
	MOBILE_NUMBER_INVALID("400.300", "Invalid MobileNumber");

	private String code;
	private String message;

	/**
	 * @param code
	 * @param message
	 */
	private ValidationErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

}
