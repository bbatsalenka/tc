package com.bbatsalenka.tc.exceptions;

public class MalformedRequestException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -981967031063364505L;

	public MalformedRequestException(String message) {
		super(message);
	}
}
