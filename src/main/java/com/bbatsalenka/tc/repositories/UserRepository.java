package com.bbatsalenka.tc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bbatsalenka.tc.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	User findByUsername(String username);
	User findByEmailAddress(String emailAddress);
	
	@Query("SELECT u FROM User u WHERE u.username = :username or u.emailAddress = :emailAddress")
	List<User> findByEmailOrUsername(@Param("username")String username, @Param("emailAddress") String emailAddress);
	
}
