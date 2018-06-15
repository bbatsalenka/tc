package com.bbatsalenka.tc.services;

import java.util.Set;

import com.bbatsalenka.tc.dtos.TestCaseDTO;

public interface TestCaseService {

	Set<TestCaseDTO> listTestCases();
	
	TestCaseDTO getTestCaseById(Integer id);
	
	TestCaseDTO save(TestCaseDTO testCaseDTO);
	
	void delete(Integer id);
	
}
