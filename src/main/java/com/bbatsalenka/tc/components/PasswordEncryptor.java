package com.bbatsalenka.tc.components;

import javax.annotation.PostConstruct;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncryptor {
	
	private BasicPasswordEncryptor passwordEncryptor;

	@PostConstruct
	public void initEncryptor() {
		passwordEncryptor = new BasicPasswordEncryptor();
	}
	
	public BasicPasswordEncryptor getBasicPasswordEncryptor() {
		return passwordEncryptor;
	}
	
}
