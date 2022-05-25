package com.msntt.MSCredit.application.exception;

public class EntityAlreadyExistsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	final private String message = "The Business Partner code not has already been registered";

	public EntityAlreadyExistsException() {

	}

	public String getMessage() {
		return message;
	}

}