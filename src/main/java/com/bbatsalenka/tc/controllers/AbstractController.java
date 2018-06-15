package com.bbatsalenka.tc.controllers;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bbatsalenka.tc.dtos.ResponseDecorator;
import com.bbatsalenka.tc.exceptions.AuthenticationException;
import com.bbatsalenka.tc.exceptions.MalformedRequestException;
import com.bbatsalenka.tc.util.Log;

@ControllerAdvice
public abstract class AbstractController {
	
	@Log
	Logger logger;

	@ExceptionHandler(value = AuthenticationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseDecorator<String> handleAuthenticationException(AuthenticationException authenticationException) {
		String errorMessageString = authenticationException.getMessage();
		ResponseDecorator<String> responseDecorator = new ResponseDecorator<>();
		responseDecorator.setMessage(errorMessageString);
		responseDecorator.setStatus(ResponseDecorator.Status.ERROR);
		return responseDecorator;
	}
	
	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseDecorator<String> handleGenericException(Exception exception) {
		logger.warn("Unknown exception", exception);
		String errorMessageString = exception.getMessage();
		ResponseDecorator<String> responseDecorator = new ResponseDecorator<>();
		responseDecorator.setMessage(errorMessageString);
		responseDecorator.setStatus(ResponseDecorator.Status.ERROR);
		return responseDecorator;
	}
	
	@ExceptionHandler(value = MalformedRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseDecorator<?> handleMalformedRequestException(MalformedRequestException exception) {
		String errorMessageString = exception.getMessage();
		ResponseDecorator<String> responseDecorator = new ResponseDecorator<>();
		responseDecorator.setMessage(errorMessageString);
		responseDecorator.setStatus(ResponseDecorator.Status.ERROR);
		return responseDecorator;
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseDecorator<?> unknownError(ResponseDecorator<?> responseDecorator, String message) {
		responseDecorator = createNewIfNull(responseDecorator);
		responseDecorator.setMessage(message);
		responseDecorator.setStatus(ResponseDecorator.Status.ERROR);
		return responseDecorator;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseDecorator<?> badRequest(ResponseDecorator<?> responseDecorator, String message) {
		responseDecorator = createNewIfNull(responseDecorator);
		responseDecorator.setMessage(message);
		responseDecorator.setStatus(ResponseDecorator.Status.ERROR);
		return responseDecorator;
	}
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseDecorator<?> unauthorized(ResponseDecorator<?> responseDecorator, String message) {
		responseDecorator = createNewIfNull(responseDecorator);
		responseDecorator.setMessage(message);
		responseDecorator.setStatus(ResponseDecorator.Status.ERROR);
		return responseDecorator;
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseDecorator<?> notFound(ResponseDecorator<?> responseDecorator, String message) {
		responseDecorator = createNewIfNull(responseDecorator);
		responseDecorator.setMessage(message);
		responseDecorator.setStatus(ResponseDecorator.Status.ERROR);
		return responseDecorator;
	}
	
	@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
	public ResponseDecorator<?> preconditionFailed(ResponseDecorator<?> responseDecorator, String message) {
		responseDecorator = createNewIfNull(responseDecorator);
		responseDecorator.setMessage(message);
		responseDecorator.setStatus(ResponseDecorator.Status.ERROR);
		return responseDecorator;
	}
	
	private ResponseDecorator<?> createNewIfNull(ResponseDecorator<?> responseDecorator) {
		if (responseDecorator == null) {
			responseDecorator = new ResponseDecorator<>();
		}
		return responseDecorator;
	}
}
