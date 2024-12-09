package com.base.demo.exception;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.base.demo.dto.DemoPojo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@Autowired
	private DemoPojo pojo;
	
	private HttpHeaders headers() {
		HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
	
	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<String> serviceExceptionHandler(ServiceException ex) throws JsonProcessingException {
		Map<String, String> errorJsonMap = new HashMap<>();
		errorJsonMap.put("FIRSTNAME", pojo.getFirstName());
		errorJsonMap.put("LASTNAME", pojo.getLastName());
		errorJsonMap.put("EMAIL", pojo.getEmail());
		errorJsonMap.put("ERROR_CODE", ex.errorCode);
		errorJsonMap.put("ERROR_TEXT", ex.errorText);
		errorJsonMap.put("ERROR_DESC", ex.errorDescription);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        pojo.setFaultResponseJson(objectMapper.writeValueAsString(errorJsonMap));
        return new ResponseEntity<String>(pojo.getFaultResponseJson(), headers(),HttpStatus.OK);
	}
	
	@ExceptionHandler(Exception.class)
	public void unhandledException(Exception ex) throws JsonProcessingException {
		ServiceException exception = new ServiceException("Internal Error","500" ,ex.getLocalizedMessage());
		serviceExceptionHandler(exception);
	}

}
