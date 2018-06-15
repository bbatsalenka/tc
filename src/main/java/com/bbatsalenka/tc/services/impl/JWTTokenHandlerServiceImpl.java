package com.bbatsalenka.tc.services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bbatsalenka.tc.authentication.UserRole;
import com.bbatsalenka.tc.dtos.TokenDTO;
import com.bbatsalenka.tc.dtos.TokenType;
import com.bbatsalenka.tc.dtos.UserCredentialsDTO;
import com.bbatsalenka.tc.dtos.UserDTO;
import com.bbatsalenka.tc.exceptions.AuthenticationException;
import com.bbatsalenka.tc.services.JWTTokenHandlerService;
import com.bbatsalenka.tc.util.Log;

@Service
public class JWTTokenHandlerServiceImpl implements JWTTokenHandlerService {

	@Value("${secret.key}")
	private String secretKey;
	
	@Value("${token.lifetime.mins}")
	private int tokenLifetimeMins;
	
	private static final int ONE_MINUTE_IN_MILLIS = 60000;
	
	@Log
	Logger logger;

	@Override
	public UserDTO getUserFromToken(String token) {
		String message = null;
		if (token == null) {
			message = "Token is missing";
			logger.warn(message);
			throw new AuthenticationException(message);
		}
		UserDTO userDTO = null;
		try {
			final Claims body = Jwts.parser().setSigningKey(secretKey)
					.parseClaimsJws(token).getBody();
			userDTO = new UserDTO();
			UserCredentialsDTO userCredentialsDTO = new UserCredentialsDTO();
			userCredentialsDTO.setUsername(body.getSubject());
			userDTO.setUserCredentialsDTO(userCredentialsDTO);
			UserRole role = UserRole.getRoleByStringValue(String.valueOf(body.get("role")));
			userDTO.setUserRole(role);
		} catch (Exception e) {
			message = "Invalid token";
			logger.warn(message, e);
			throw new AuthenticationException(message);
		}
		return userDTO;
	}
	
	@Override
	public long getExpirationFromToken(String token) {
		String message = null;
		if (token == null) {
			message = "Token is missing";
			logger.warn(message);
			throw new AuthenticationException(message);
		}
		long expires = 0L;
		try {
			final Claims body = Jwts.parser().setSigningKey(secretKey)
					.parseClaimsJws(token).getBody();
			expires = (long) body.get("exp");
		} catch (Exception e) {
			message = "Invalid token";
			logger.warn(message);
			throw new AuthenticationException(message);
		}
		return expires;
	}
	
	@Override
	public TokenDTO generateToken(UserDTO userDTO) {
			long expires = System.currentTimeMillis() + tokenLifetimeMins * ONE_MINUTE_IN_MILLIS;
	        Claims claims = Jwts.claims().setSubject(userDTO.getUserCredentialsDTO().getUsername());
	        claims.put("role", userDTO.getUserRole().getRole());
	        claims.put("exp", expires);

	        String token = Jwts.builder()
	                .setClaims(claims)
	                .signWith(SignatureAlgorithm.HS512, secretKey)
	                .compact();
	        
	        return new TokenDTO(token, tokenLifetimeMins * ONE_MINUTE_IN_MILLIS, TokenType.BEARER);
	}

}