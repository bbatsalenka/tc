package com.bbatsalenka.tc.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbatsalenka.tc.components.PasswordEncryptor;
import com.bbatsalenka.tc.services.PasswordHandlingService;

@Service
public class PasswordHandlingServiceImpl implements PasswordHandlingService {

	@Autowired
	private PasswordEncryptor passwordEncryptor;

	@Override
	public String hashPasswordForSaving(String password) {
		String encryptedPassword = null;
		encryptedPassword = passwordEncryptor.getBasicPasswordEncryptor()
				.encryptPassword(password);
		return encryptedPassword;
	}

	@Override
	public boolean passwordsMatch(String inputPassword, String encryptedPassword) {
		boolean passwordsMatch = passwordEncryptor.getBasicPasswordEncryptor()
				.checkPassword(inputPassword, encryptedPassword);
		return passwordsMatch;
	}

}
