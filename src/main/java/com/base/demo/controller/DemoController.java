package com.base.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.demo.exception.ServiceException;
import com.base.demo.service.DemoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import net.sf.saxon.s9api.SaxonApiException;

@RestController
@RequestMapping("/api/v1")
public class DemoController {
	
	@Autowired
	private DemoService service;
	
	private HttpHeaders headers() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
	
	@PostMapping("/getResponse")
	public HttpEntity<String> getResponse(@RequestBody String inputXML) throws JsonMappingException, JsonProcessingException, SaxonApiException, ServiceException{
		return new ResponseEntity<String>(service.getResponse(inputXML),headers(), HttpStatus.OK);
	}
}
