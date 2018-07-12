package com.portal.exception;

public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 8675007456900089437L;

	public final String errorCode;
	public final String status;
	public final String message;

	public BaseException(String exception, String errorCode, String status, String message) {
		super(exception);
		this.errorCode = errorCode;
		this.status = status;
		this.message = message;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}
}
