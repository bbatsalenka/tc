package com.bbatsalenka.tc.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbatsalenka.tc.domain.User;
import com.bbatsalenka.tc.dtos.UserDTO;
import com.bbatsalenka.tc.repositories.UserRepository;
import com.bbatsalenka.tc.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		User userToBeSaved = new User(userDTO);
		User savedUser = userRepository.save(userToBeSaved);
		return new UserDTO(savedUser);
	}
	
	@Override
	public UserDTO getUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		return new UserDTO(user);
	}
	
	@Override
	public UserDTO getUserByEmailAddress(String emailAddress) {
		User user = userRepository.findByEmailAddress(emailAddress);
		return new UserDTO(user);
	}
	
	@Override
	public Set<UserDTO> getUsersByUsernameOrEmail(String username, String emailAddress) {
		List<User> foundUsers = userRepository.findByEmailOrUsername(username, emailAddress);
		Set<UserDTO> foundUserDTOs = null;
		if (foundUsers != null && foundUsers.size() > 0) {
			foundUserDTOs = new HashSet<>();
			for (User user : foundUsers) {
				foundUserDTOs.add(new UserDTO(user));
			}
		}
		return foundUserDTOs;
	}

}
