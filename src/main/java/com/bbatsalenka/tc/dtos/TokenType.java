package com.bbatsalenka.tc.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TokenType {
	@JsonProperty(value = "Bearer")
	BEARER("Bearer"), 
	
	@JsonProperty(value = "Basic")
	BASIC("Basic");
	
	private String tokenType;
	
	private TokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getTokenType() {
		return tokenType;
	}
}

