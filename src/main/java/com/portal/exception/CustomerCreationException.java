package com.portal.exception;

public class CustomerCreationException extends BaseException {

	private static final long serialVersionUID = 1L;

	public CustomerCreationException(String errorCode, String status, String message) {
		super("CustomerCreationException", errorCode, status, message);
	}

}
