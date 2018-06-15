package com.bbatsalenka.tc.exceptions;

public class MissingRequiredParametersException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5285006707108595973L;

	public MissingRequiredParametersException(String message) {
		super(message);
	}
	
}
