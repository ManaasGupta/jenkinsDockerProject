package com.base.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.demo.dto.DemoPojo;
import com.base.demo.exception.ServiceException;
import com.base.demo.util.Utility;
import com.base.demo.valdiation.Validations;

@Service
public class DemoService {
	
	@Autowired
	private DemoPojo demoPojo;
	
	@Autowired
	private Validations validation;
	
	@Autowired
	Utility utility;

	public String getResponse(String inputXML) throws ServiceException {
		try {
		demoPojo.setRequestXML(inputXML);
		validation.validateXML(demoPojo.getRequestXML());
		utility.convertXML2Json(demoPojo.getRequestXML());
		return demoPojo.getRequestJson();
		}catch (Exception e) {
			throw new ServiceException("Error Processing Message :"+ demoPojo.getRequestXML(), "400");
		}
	}

}
