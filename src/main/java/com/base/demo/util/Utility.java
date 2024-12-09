package com.base.demo.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.demo.dto.DemoPojo;
import com.base.demo.exception.ServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class Utility {
	
	@Autowired
	private DemoPojo demoPojo;

	public void convertXML2Json(String requestXML) throws ServiceException {
		try {
		Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("FIRSTNAME", demoPojo.getFirstName());
        jsonMap.put("LASTNAME", demoPojo.getLastName());
        jsonMap.put("EMAIL", demoPojo.getEmail());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        demoPojo.setRequestJson(objectMapper.writeValueAsString(jsonMap));
		}catch (Exception ex) {
			throw new ServiceException("Error Ouccured while transforming Request: "+demoPojo.getRequestXML(), "500");
		}
	}

}
