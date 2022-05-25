package com.msntt.MSCredit.application.exception;

public class EntityNotExistsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message = "The Resource you are looking for doesn't exists or has been deleted";

	public EntityNotExistsException() {

	}

	public String getMessage() {
		return message;
	}
}