package com.base.demo.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Component
public class DemoPojo {
	
	private String firstName;
	private String lastName;
	private String email;
	private String requestXML;
	private String responseXML;
	private String requestJson;
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getRequestXML() {
		return requestXML;
	}
	
	public void setRequestXML(String requestXML) {
		this.requestXML = requestXML;
	}
	
	public String getResponseXML() {
		return responseXML;
	}
	
	public void setResponseXML(String responseXML) {
		this.responseXML = responseXML;
	}
	public String getRequestJson() {
		return requestJson;
	}
	
	public void setRequestJson(String requestJson) {
		this.requestJson = requestJson;
	}
}
