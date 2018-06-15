package com.bbatsalenka.tc.exceptions;

public class AuthenticationException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8194918158560441713L;

	public AuthenticationException(String message) {
		super(message);
	}
	
}
