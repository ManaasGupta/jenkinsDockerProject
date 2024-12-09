package com.base.demo.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.base.demo.dto.DemoPojo;
import com.fasterxml.jackson.core.JsonProcessingException;

class GlobalExceptionHandlerTest {
	
	@InjectMocks
	private GlobalExceptionHandler globalExceptionHandler;
	
	@Mock
	private DemoPojo pojo;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void excptionHandlerTest() throws JsonProcessingException {
		Exception exception  = new Exception();
		globalExceptionHandler.unhandledException(exception);
	}

}
