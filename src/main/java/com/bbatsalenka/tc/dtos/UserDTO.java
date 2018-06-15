package com.bbatsalenka.tc.dtos;

import javax.validation.constraints.NotNull;

import com.bbatsalenka.tc.authentication.UserRole;
import com.bbatsalenka.tc.domain.User;
import com.bbatsalenka.tc.util.ExcludeFromMerge;

public class UserDTO {

	@ExcludeFromMerge
	private Integer id;
	@NotNull(message = "Missing email address")
	private String emailAddress;
	private UserCredentialsDTO userCredentials;
	private UserRole userRole;
	
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public UserCredentialsDTO getUserCredentialsDTO() {
		return userCredentials;
	}
	public void setUserCredentialsDTO(UserCredentialsDTO userCredentials) {
		this.userCredentials = userCredentials;
	}
	public UserRole getUserRole() {
		return userRole;
	}
	
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public UserDTO() {}
	
	public UserDTO(User user) {
		if (user != null) {
			UserCredentialsDTO userCredentialsDTO = new UserCredentialsDTO();
			userCredentialsDTO.setUsername(user.getUsername());
			userCredentialsDTO.setPassword(user.getPassword());
			setUserCredentialsDTO(userCredentialsDTO);
			setId(user.getId());
			setUserRole(user.getUserRole());
			setEmailAddress(user.getEmailAddress());
		}
	}
	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", emailAddress=" + emailAddress
				+ ", userCredentials=" + userCredentials + ", userRole="
				+ userRole + "]";
	}
	
}
