package com.base.demo.valdiation;

import java.io.StringReader;

import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.demo.constant.DemoConstants;
import com.base.demo.dto.DemoPojo;
import com.base.demo.exception.ServiceException;

import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XPathCompiler;
import net.sf.saxon.s9api.XPathSelector;
import net.sf.saxon.s9api.XdmNode;

@Service
public class Validations {
	
	@Autowired
	private DemoPojo demoPojo;

	public void validateXML(String requestXML) throws SaxonApiException, ServiceException {
		Processor processor = new Processor(false);
		XPathCompiler xPathCompilerCompiler = processor.newXPathCompiler();
		DocumentBuilder builder = processor.newDocumentBuilder();
		XdmNode docNode = builder.build(new StreamSource(new StringReader(requestXML)));
		
		String firstNameValue = getXDMValue(docNode,xPathCompilerCompiler,DemoConstants.FIRSTNAMEXPATH);
		demoPojo.setFirstName(firstNameValue);
		
		String lastNameValue = getXDMValue(docNode,xPathCompilerCompiler,DemoConstants.LASTNAMEXPATH); 
		demoPojo.setLastName(lastNameValue);
		
		String emailValue = getXDMValue(docNode,xPathCompilerCompiler,DemoConstants.EMAILXPATH);
		demoPojo.setEmail(emailValue);
		
		if (StringUtils.isAnyEmpty(demoPojo.getFirstName(),demoPojo.getLastName(),demoPojo.getEmail())) {
			throw new ServiceException("1000","Invalid XML");
		}
	}

	private String getXDMValue(XdmNode docNode, XPathCompiler xPathCompilerCompiler, String xpath) throws SaxonApiException {
		XPathSelector selector = xPathCompilerCompiler.compile(xpath).load();
		selector.setContextItem(docNode);
		return selector.evaluate().toString();
	}

}
