package com.portal.exception;

public class CustomerNotFoundException extends BaseException {

	private static final long serialVersionUID = 1L;

	public CustomerNotFoundException(String errorCode, String status, String message) {
		super("CustomerNotFoundException", errorCode, status, message);
	}

}
