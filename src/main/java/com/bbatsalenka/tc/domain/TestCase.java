package com.bbatsalenka.tc.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.bbatsalenka.tc.dtos.TestCaseDTO;

@Entity
@Table(name = "test_case")
public class TestCase {

	public TestCase() {
	}

	{
		embeddableDates = new EmbeddableDates();
	}

	public TestCase(TestCaseDTO testCaseDTO) {
		this.id = testCaseDTO.getId();
		this.description = testCaseDTO.getDescription();
		this.title = testCaseDTO.getTitle();
		this.steps = testCaseDTO.getSteps();
		this.version = testCaseDTO.getVersion();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "steps")
	private String steps;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	// TODO
	// add tag - this will be a separate table

	// TODO
	// add author - this will be a separate table

	@Embedded
	EmbeddableDates embeddableDates;

	@Version
	@Column(name = "version")
	private Integer version;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getSteps() {
		return steps;
	}

	public void setSteps(String steps) {
		this.steps = steps;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "TestCase [id=" + id + ", title=" + title + ", description="
				+ description + ", steps=" + steps + ", embeddableDates="
				+ embeddableDates + "]";
	}

}
