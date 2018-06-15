package com.bbatsalenka.tc.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bbatsalenka.tc.authentication.UserRole;
import com.bbatsalenka.tc.dtos.UserCredentialsDTO;
import com.bbatsalenka.tc.dtos.UserDTO;

@Entity
@Table(name = "user")
public class User {
	
	{
		embeddableDates = new EmbeddableDates();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "username", unique = true)
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "user_role")
	@Enumerated(EnumType.STRING)
	private UserRole userRole;
	
	@Column(name = "email_address", unique = true)
	private String emailAddress;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<TestCase> testCases;

	@Embedded
	EmbeddableDates embeddableDates;
	
	public User() {}
	
	public User(UserDTO userDTO) {
		if (userDTO != null) {
			setId(userDTO.getId());
			UserCredentialsDTO userCredentials = userDTO.getUserCredentialsDTO();
			setPassword(userCredentials.getPassword());
			setUsername(userCredentials.getUsername());
			setUserRole(userDTO.getUserRole());
			setEmailAddress(userDTO.getEmailAddress());
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public EmbeddableDates getEmbeddableDates() {
		return embeddableDates;
	}

	public void setEmbeddableDates(EmbeddableDates embeddableDates) {
		this.embeddableDates = embeddableDates;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Set<TestCase> getTestCases() {
		return testCases;
	}

	public void setTestCases(Set<TestCase> testCases) {
		this.testCases = testCases;
	}

}
