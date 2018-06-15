package com.bbatsalenka.tc.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bbatsalenka.tc.dtos.ResponseDecorator;
import com.bbatsalenka.tc.dtos.TestCaseDTO;
import com.bbatsalenka.tc.services.TestCaseService;
import com.bbatsalenka.tc.util.Log;
import com.bbatsalenka.tc.util.Utils;

@RestController
@RequestMapping("/testcases")
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
public class TestCaseController extends AbstractController {
	
	@Log
	Logger logger;
	
	@Autowired
	private TestCaseService testCaseService;

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
    public ResponseDecorator<?> getTestCases() {
		ResponseDecorator<Set<TestCaseDTO>> responseDecorator = new ResponseDecorator<>();
		responseDecorator.setData(testCaseService.listTestCases());
        return responseDecorator;
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
    public ResponseDecorator<?> getTestCaseById(@PathVariable Integer id, HttpServletResponse response) {
		ResponseDecorator<TestCaseDTO> responseDecorator = new ResponseDecorator<>();
		TestCaseDTO testCaseDTO = testCaseService.getTestCaseById(id);
		if (testCaseDTO != null) {
			responseDecorator.setData(testCaseDTO);
		} else {
			String errorMessage = "Could not find a test case with id= " + id;
			logger.warn(errorMessage);
			return notFound(responseDecorator, errorMessage);
		}
		Integer version = testCaseDTO.getVersion();
		response.setHeader("ETag", String.valueOf(version));
		responseDecorator.add(linkTo(methodOn(TestCaseController.class).getTestCaseById(id, response)).withRel("testCase"));
        return responseDecorator;
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseDecorator<?> updateTestCase(@PathVariable Integer id, @Valid @RequestBody TestCaseDTO testCaseDTO,
			BindingResult bindingResult, @RequestHeader("If-Match") Integer version) {
		ResponseDecorator<TestCaseDTO> responseDecorator = new ResponseDecorator<>();
		String errorMessage = null;
		if (bindingResult.hasErrors()) {
			errorMessage = "Missing required parameters: " + bindingResult.getFieldError().getDefaultMessage();
			logger.warn(errorMessage);
			return badRequest(responseDecorator, errorMessage);
		}
		TestCaseDTO testCaseDTOForUpdate = testCaseService.getTestCaseById(id);
		Utils.mergeTwoObjects(testCaseDTO, testCaseDTOForUpdate);
		testCaseDTOForUpdate.setVersion(version);
		TestCaseDTO updatedTestCase = null;
		try {
			updatedTestCase = testCaseService.save(testCaseDTOForUpdate);
		} catch (ObjectOptimisticLockingFailureException oolfe) {
			errorMessage = "Test case with id " + testCaseDTOForUpdate.getId() + " was updated or deleted by another "
					+ "transaction";
			logger.warn(errorMessage, oolfe);
			return preconditionFailed(responseDecorator, errorMessage);
		}
		responseDecorator.setData(updatedTestCase);
		responseDecorator.add(linkTo(methodOn(TestCaseController.class).updateTestCase(id, testCaseDTO, bindingResult,
				version)).withRel("testCase"));
			
		return responseDecorator;
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseDecorator<?> createTestCase(@Valid @RequestBody TestCaseDTO testCaseDTO, BindingResult result, 
			HttpServletResponse httpServletResponse) {
		ResponseDecorator<TestCaseDTO> responseDecorator = new ResponseDecorator<>();
		if (result.hasErrors()) {
			String errorMessage = "Missing required parameters: " + result.getFieldError().getDefaultMessage();
			logger.warn(errorMessage);
			return badRequest(responseDecorator, errorMessage);
		}
		TestCaseDTO savedDTO = testCaseService.save(testCaseDTO);
		responseDecorator.setData(savedDTO);
		responseDecorator.add(linkTo(methodOn(TestCaseController.class).getTestCaseById(savedDTO.getId(), httpServletResponse))
				.withRel("testCase"));
		return responseDecorator;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseDecorator<?> deleteTestCase(@PathVariable Integer id) {
		ResponseDecorator<TestCaseDTO> responseDecorator = new ResponseDecorator<>();
		testCaseService.delete(id);
		return responseDecorator;
	}
	
}