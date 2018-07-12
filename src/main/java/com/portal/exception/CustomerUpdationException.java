package com.portal.exception;

public class CustomerUpdationException extends BaseException {

	private static final long serialVersionUID = 1L;

	public CustomerUpdationException(String errorCode, String status, String message) {
		super("CustomerUpdationException", errorCode, status, message);
	}

}
