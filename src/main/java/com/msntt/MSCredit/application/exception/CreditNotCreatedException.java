package com.msntt.MSCredit.application.exception;

public class CreditNotCreatedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message = "Account couldn't be created";
	
	public CreditNotCreatedException() {

	}

	public String getMessage() {
		return message;
	}}
