package com.bbatsalenka.tc.controllers;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bbatsalenka.tc.dtos.ResponseDecorator;
import com.bbatsalenka.tc.dtos.TokenDTO;
import com.bbatsalenka.tc.dtos.UserCredentialsDTO;
import com.bbatsalenka.tc.dtos.UserDTO;
import com.bbatsalenka.tc.services.JWTTokenHandlerService;
import com.bbatsalenka.tc.services.PasswordHandlingService;
import com.bbatsalenka.tc.services.UserService;
import com.bbatsalenka.tc.util.Log;
import com.bbatsalenka.tc.util.Utils;

@RestController
@RequestMapping(value = "/token")
public class TokenController extends AbstractController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordHandlingService passwordHandlingService;
	
	@Autowired
	private JWTTokenHandlerService jWTTokenHandlerService;
	
	@Log
	Logger logger;
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseDecorator<?> getToken(@RequestHeader("Authorization") String basicAuthHeader) {
		UserCredentialsDTO decodedCredentials = null;
		String message = null;
		ResponseDecorator<TokenDTO> responseDecorator = new ResponseDecorator<>();
		try {
			decodedCredentials = Utils.decodeBase64Credentials(basicAuthHeader);
		} catch (Exception e) {
			message = "Could not parse header with credentials";
			logger.warn(message);
			return badRequest(responseDecorator, message);
		}
		UserDTO userDTO = userService.getUserByUsername(decodedCredentials.getUsername());
		if (userDTO == null) {
			message = "Invalid user credentials provided";
			logger.warn(message);
			return unauthorized(responseDecorator, message);
		}
		boolean passwordsMatch = passwordHandlingService.passwordsMatch(decodedCredentials.getPassword(), 
				userDTO.getUserCredentialsDTO().getPassword());
		if (!passwordsMatch) {
			message = "Provided password is invalid";
			logger.info(message);
			return unauthorized(responseDecorator, message);
		}
		TokenDTO tokenDTO = jWTTokenHandlerService.generateToken(userDTO);
		responseDecorator.setData(tokenDTO);
		
		return responseDecorator;
	}
	
}
