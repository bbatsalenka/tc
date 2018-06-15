package com.bbatsalenka.tc.dtos;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class UserCredentialsDTO {

	@NotNull(message = "Missing username")
	private String username;
	
	@NotNull(message = "Missing password")
	@JsonProperty(access = Access.WRITE_ONLY)
	@JsonInclude(Include.NON_NULL)
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "UserCredentialsDTO [username=" + username + ", password=****]";
	}
	
}
