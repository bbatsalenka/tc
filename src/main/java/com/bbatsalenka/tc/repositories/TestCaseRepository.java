package com.bbatsalenka.tc.repositories;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.bbatsalenka.tc.domain.TestCase;

public interface TestCaseRepository extends CrudRepository<TestCase, Integer> {

	Set<TestCase> findAll();
	
}
