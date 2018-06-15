package com.bbatsalenka.tc.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;


public class TokenDTO {

	@JsonProperty(value = "access_token")
	private String accessToken;
	
	@JsonProperty(value = "expires_in")
	private long expiresIn;
	
	@JsonProperty(value = "token_type")
	private TokenType tokenType;
	
	public TokenDTO() {
	}
	
	public TokenDTO(String token, long expiresIn, TokenType tokenType) {
		this.accessToken = token;
		this.expiresIn = expiresIn;
		this.tokenType = tokenType;
	}

	public long getExpiresIn() {
		return expiresIn;
	}
	
	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public TokenType getTokenType() {
		return tokenType;
	}

	public void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}
	
}
