package com.base.demo.util;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.json.JsonParseException;

import com.base.demo.dto.DemoPojo;
import com.base.demo.exception.ServiceException;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class UtilityTest {
	@Mock
	private DemoPojo demoPojo;

	@InjectMocks
	Utility utility;

	@Mock
	ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testForException() throws JsonProcessingException {
		Map<String, String> jsonMap = new HashMap<>();
		jsonMap.put("FIRSTNAME", demoPojo.getFirstName());
		jsonMap.put("LASTNAME", demoPojo.getLastName());
		jsonMap.put("EMAIL", demoPojo.getEmail());
		jsonMap.put("ERROR_CODE", "200");
		jsonMap.put("ERROR_TEXT", "OK");
		jsonMap.put("ERROR_DESC", "SUCCESS");
		when(objectMapper.writeValueAsString(jsonMap))
				.thenThrow(new JsonProcessingException("Error occurred during JSON processing") {
				});

		assertThrows(ServiceException.class, () -> utility.convertXML2Json());
	}

}
