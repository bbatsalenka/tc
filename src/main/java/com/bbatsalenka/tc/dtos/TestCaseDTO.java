package com.bbatsalenka.tc.dtos;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.bbatsalenka.tc.domain.TestCase;
import com.bbatsalenka.tc.util.ExcludeFromMerge;

public class TestCaseDTO {

	public TestCaseDTO(TestCase testCase) {
		this.id = testCase.getId();
		this.title = testCase.getTitle();
		this.description = testCase.getDescription();
		this.created = testCase.getEmbeddableDates().getCreated();
		this.updated = testCase.getEmbeddableDates().getUpdated();
		this.steps = testCase.getSteps();
		this.version = testCase.getVersion();
	}
	
	public TestCaseDTO() {}
	
	@ExcludeFromMerge
	private Integer id;
	@NotNull(message = "Title cannot be empty")
	private String title;
	private String description;
	private String steps;
	@ExcludeFromMerge
	private Date created;
	@ExcludeFromMerge
	private Date updated;
	@ExcludeFromMerge
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
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSteps() {
		return steps;
	}
	public void setSteps(String steps) {
		this.steps = steps;
	}
	
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((steps == null) ? 0 : steps.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((updated == null) ? 0 : updated.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestCaseDTO other = (TestCaseDTO) obj;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (steps == null) {
			if (other.steps != null)
				return false;
		} else if (!steps.equals(other.steps))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (updated == null) {
			if (other.updated != null)
				return false;
		} else if (!updated.equals(other.updated))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TestCaseDTO [id=" + id + ", title=" + title + ", description="
				+ description + ", steps=" + steps + ", created=" + created
				+ ", updated=" + updated + ", version=" + version + "]";
	}
	
}
