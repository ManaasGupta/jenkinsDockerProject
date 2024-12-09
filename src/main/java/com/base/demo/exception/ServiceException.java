package com.base.demo.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public String errorText;
	public String errorCode;
	
	public ServiceException(String errorText,String errorCode) {
		this.errorCode=errorCode;
		this.errorText=errorText;
	}
}
