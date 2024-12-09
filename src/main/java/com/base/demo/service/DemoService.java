package com.base.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.demo.dto.DemoPojo;
import com.base.demo.exception.ServiceException;
import com.base.demo.util.Utility;
import com.base.demo.valdiation.Validations;

import net.sf.saxon.s9api.SaxonApiException;

@Service
public class DemoService {
	
	@Autowired
	private DemoPojo demoPojo;
	
	@Autowired
	private Validations validation;
	
	@Autowired
	Utility utility;

	public String getResponse(String inputXML) throws ServiceException{
		demoPojo.setRequestXML(inputXML);
		validation.validateXML(demoPojo.getRequestXML());
		utility.convertXML2Json();
		return demoPojo.getRequestJson();
	}

}
