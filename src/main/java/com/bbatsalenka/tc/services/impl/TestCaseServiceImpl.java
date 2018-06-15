package com.bbatsalenka.tc.services.impl;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.bbatsalenka.tc.domain.TestCase;
import com.bbatsalenka.tc.dtos.TestCaseDTO;
import com.bbatsalenka.tc.exceptions.MalformedRequestException;
import com.bbatsalenka.tc.repositories.TestCaseRepository;
import com.bbatsalenka.tc.services.TestCaseService;
import com.bbatsalenka.tc.util.Log;

@Service
public class TestCaseServiceImpl implements TestCaseService {
	
	@Log
	Logger logger;

	@Autowired
	private TestCaseRepository testCaseRepository;
	
	@Override
	public Set<TestCaseDTO> listTestCases() {
		Set<TestCase> testCases = testCaseRepository.findAll();
		Set<TestCaseDTO> testCaseDTOs = new HashSet<>();
		for (TestCase testCase : testCases) {
			testCaseDTOs.add(new TestCaseDTO(testCase));
		}
		return testCaseDTOs;
	}
	
	@Override
	public TestCaseDTO save(TestCaseDTO testCaseDTO) {
		TestCase testCase = new TestCase(testCaseDTO);
		return new TestCaseDTO(testCaseRepository.save(testCase));
	}
	
	@Override
	public TestCaseDTO getTestCaseById(Integer id) {
		TestCase testCase = testCaseRepository.findOne(id);
		if (testCase != null) {
			return new TestCaseDTO(testCase);
		}
		return null;
	}
	
	@Override
	public void delete(Integer id) {
		try {
			testCaseRepository.delete(id);
		} catch (EmptyResultDataAccessException e) {
			logger.warn("Test case with id {} does not exist", id);
			throw new MalformedRequestException("Could not delete test case with id " + id);
		}
	}
}
