package com.bbatsalenka.tc.services;

public interface PasswordHandlingService {

	String hashPasswordForSaving(String password);
	boolean passwordsMatch(String inputPassword, String encryptedPassword);
	
}
