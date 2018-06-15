package com.bbatsalenka.tc.dtos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ResponseDecorator<T> extends ResourceSupport {

	private T data;
	private String message;
	private Status status;
	
	{
		status = Status.SUCCESS;
	}
	
	public enum Status {
		ERROR("ERROR"), SUCCESS("SUCCESS"), UNKNOWN("UNKNOWN");
		
		private String status;
		
		private Status(String status) {
			this.status = status;
		}
		
		public String getStatus() {
			return status;
		}
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	// override to not return empty list in json
	@JsonInclude(Include.NON_EMPTY)
	@Override
	public List<Link> getLinks() {
		return super.getLinks();
	}
	
}
