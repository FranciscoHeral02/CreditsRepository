package com.msntt.MSCredit.application.exception;

public class HasAccountException extends Exception {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String message = "You have a Credit with us";

	public HasAccountException() {

	}

	public String getMessage() {
		return message;
	}
}