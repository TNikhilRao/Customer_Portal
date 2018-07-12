package com.portal.exception;


public class ValidationException extends BaseException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor to call super class constructor and initialize errorCode
     * 
     * @param errorCode
     */
    public ValidationException(String errorCode, String message) {
        super("ValidationException", errorCode, "failure", message);
    }
}
