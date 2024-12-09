package com.base.demo.valdiation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.base.demo.dto.DemoPojo;
import com.base.demo.exception.ServiceException;

class ValidationsTest {
	
	@Mock
	private DemoPojo demoPojo;
	
	@InjectMocks
	private Validations validations;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testForExecption() throws ServiceException {
		String improperString = "<soap:Envelope\r\n"
				+ "xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\"\r\n"
				+ "soap:encodingStyle=\"http://www.w3.org/2003/05/soap-encoding\">\r\n"
				+ "    <soap:Body xmlns=\"http://www.example.com\">\r\n"
				+ "        <firstName>Manas</firstName>\r\n"
				+ "        <lastName></lastName>\r\n"
				+ "        <email>manasggpt3@gmail.com</email1>\r\n"
				+ "    </soap:Body>\r\n"
				+ "</soap:Envelope>";
		
		
		
		assertThrows(ServiceException.class, () -> validations.validateXML(improperString));
	}
	

}
