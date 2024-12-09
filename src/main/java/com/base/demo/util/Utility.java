package com.base.demo.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.demo.dto.DemoPojo;
import com.base.demo.exception.ServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class Utility {
	
	@Autowired
	private DemoPojo demoPojo;
	
	@Autowired
	ObjectMapper objectMapper;

	public void convertXML2Json() throws ServiceException{
		try {
		Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("FIRSTNAME", demoPojo.getFirstName());
        jsonMap.put("LASTNAME", demoPojo.getLastName());
        jsonMap.put("EMAIL", demoPojo.getEmail());
        jsonMap.put("ERROR_CODE", "200");
        jsonMap.put("ERROR_TEXT", "OK");
        jsonMap.put("ERROR_DESC", "SUCCESS");
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        demoPojo.setRequestJson(objectMapper.writeValueAsString(jsonMap));
		}catch (JsonProcessingException ex) {
			throw new ServiceException("Error Ouccured while transforming1", "500",ex.getLocalizedMessage());
		}
	}

}
