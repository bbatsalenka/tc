package com.bbatsalenka.tc.services.impl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.bbatsalenka.tc.authentication.UserAuthentication;
import com.bbatsalenka.tc.dtos.UserDTO;
import com.bbatsalenka.tc.exceptions.AuthenticationException;
import com.bbatsalenka.tc.services.JWTAuthenticationService;
import com.bbatsalenka.tc.services.JWTTokenHandlerService;
import com.bbatsalenka.tc.util.Constants;
import com.bbatsalenka.tc.util.Log;
import com.bbatsalenka.tc.util.Utils;

@Service
public class JWTAuthenticationServiceImpl implements JWTAuthenticationService {

	@Autowired
	JWTTokenHandlerService jWTTokenHandlerService;
	
	@Log
	Logger logger;
	
	@Override
	public Authentication getAuthentication(HttpServletRequest request) {
		final String headerValue = request.getHeader(Constants.AUTHORIZATION);
		String message = null;
		if (headerValue == null || !headerValue.startsWith(Constants.BEARER)) {
			message = "Missing or invalid Authorization header.";
			logger.warn(message);
			throw new AuthenticationException(message);
		}
		final String token = Utils.getToken(headerValue);
		UserDTO userDTO = jWTTokenHandlerService.getUserFromToken(token);
		long expires = jWTTokenHandlerService.getExpirationFromToken(token);
		if (expires < System.currentTimeMillis()) {
			message = "Token is expired";
			throw new AuthenticationException(message);
		}
		Authentication authentication = new UserAuthentication(userDTO);
		return authentication;
	}

}
