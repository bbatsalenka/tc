package com.bbatsalenka.tc.services;

import java.util.Set;

import com.bbatsalenka.tc.dtos.UserDTO;

public interface UserService {

	UserDTO createUser(UserDTO userDTO);
	
	UserDTO getUserByUsername(String username);
	
	UserDTO getUserByEmailAddress(String emailAddress);
	
	Set<UserDTO> getUsersByUsernameOrEmail(String username, String emailAddress);
}
