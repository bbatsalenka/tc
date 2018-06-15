package com.bbatsalenka.tc.authentication;

import org.springframework.security.core.GrantedAuthority;


public enum UserRole implements GrantedAuthority {
	
	USER("USER"), ADMIN("ADMIN");
	
	private String role;
	
	private UserRole(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
	
	public static UserRole getRoleByStringValue(String role) {
		UserRole userRole = null;
		for (UserRole value : UserRole.values()) {
			if (value.getRole().equalsIgnoreCase(role)) {
				userRole = value;
				break;
			}
		}
		return userRole;
	}

	@Override
	public String getAuthority() {
		return role;
	}
}
