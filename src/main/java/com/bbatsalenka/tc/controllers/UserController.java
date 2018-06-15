package com.bbatsalenka.tc.controllers;

import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bbatsalenka.tc.dtos.ResponseDecorator;
import com.bbatsalenka.tc.dtos.UserDTO;
import com.bbatsalenka.tc.services.PasswordHandlingService;
import com.bbatsalenka.tc.services.UserService;
import com.bbatsalenka.tc.util.Log;

@RestController
@RequestMapping(value = "/users")
public class UserController extends AbstractController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	private PasswordHandlingService passwordHandlingService;
	
	@Log
	Logger logger;
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseDecorator<?> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult result) {
		UserDTO newUserDTO = null;
		ResponseDecorator<UserDTO> responseDecorator = new ResponseDecorator<>();
		String errorMessage = null;
		if (result.hasErrors()) {
			errorMessage = "Parameters are either invalid or missing: " + result.getFieldError().getDefaultMessage();
			logger.warn(errorMessage);
			return badRequest(responseDecorator, errorMessage);
		}
		Set<UserDTO> usersWithSameUsernameOrEmail = userService.getUsersByUsernameOrEmail(
				userDTO.getUserCredentialsDTO().getUsername(), userDTO.getEmailAddress());
		if (usersWithSameUsernameOrEmail != null && usersWithSameUsernameOrEmail.size() > 0) {
			StringBuilder errorMessages = new StringBuilder();
			for (UserDTO userWithSameEmailOrUsername : usersWithSameUsernameOrEmail) {
				if (userWithSameEmailOrUsername.getEmailAddress().equals(userDTO.getEmailAddress())) {
					errorMessage = "Email address " + userDTO.getEmailAddress() + " is utilized by another user. ";
					logger.warn(errorMessage);
					errorMessages.append(errorMessage);
				} 
				if (userDTO.getUserCredentialsDTO().getUsername()
						.equals(userWithSameEmailOrUsername.getUserCredentialsDTO().getUsername())) {
					errorMessage = "Username " + userDTO.getUserCredentialsDTO().getUsername() + " is taken by another user. ";
					logger.warn(errorMessage);
					errorMessages.append(errorMessage);
				}
			}
			return badRequest(responseDecorator, errorMessages.toString());
		}
		logger.info("Creating user with following parameters {}", userDTO);
		String encryptedPasssword = passwordHandlingService.hashPasswordForSaving(userDTO.getUserCredentialsDTO()
				.getPassword());
		userDTO.getUserCredentialsDTO().setPassword(encryptedPasssword);
		newUserDTO = userService.createUser(userDTO);
		responseDecorator.setData(newUserDTO);
		
		return responseDecorator;
	}
}
