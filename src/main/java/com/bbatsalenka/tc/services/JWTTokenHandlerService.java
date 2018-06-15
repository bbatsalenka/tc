package com.bbatsalenka.tc.services;

import com.bbatsalenka.tc.dtos.TokenDTO;
import com.bbatsalenka.tc.dtos.UserDTO;

public interface JWTTokenHandlerService {

	UserDTO getUserFromToken(String token);
	
	long getExpirationFromToken(String token);
	
	TokenDTO generateToken(UserDTO userDTO);
	
}
