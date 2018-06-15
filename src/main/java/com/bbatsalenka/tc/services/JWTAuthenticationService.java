package com.bbatsalenka.tc.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

public interface JWTAuthenticationService {

	Authentication getAuthentication(HttpServletRequest request);
	
}
