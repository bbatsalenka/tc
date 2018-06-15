package com.bbatsalenka.tc.authentication;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.bbatsalenka.tc.dtos.UserDTO;

public class UserAuthentication implements Authentication {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4970077446915368965L;
	private UserDTO userDTO;
	private boolean isAuthenticated;
	private UserRole userRole;
	
	public UserAuthentication(UserDTO userDTO) {
		this.userDTO = userDTO;
		setAuthenticated(true);
		this.userRole = userDTO.getUserRole();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(this.userRole);
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return userDTO;
	}

	@Override
	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	@Override
	public void setAuthenticated(boolean arg0) throws IllegalArgumentException {
		isAuthenticated = arg0;
	}

}
