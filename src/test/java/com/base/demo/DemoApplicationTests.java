package com.base.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class DemoApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	TestRestTemplate restTemplate;
	HttpHeaders headers = new HttpHeaders();

	private String urlEndpoint = "/api/v1/getResponse";
	private static String outputFolderPath = "src/test/resources/output";

	private String getUrl(String endpoint) {
		return "http://localhost:" + port + endpoint;
	}

	@BeforeAll
	static void setUp() throws IOException {
		Path outputPath = Paths.get(outputFolderPath);
		Files.createDirectories(outputPath);
		File outputDirectory = new File(outputFolderPath);
		FileUtils.cleanDirectory(outputDirectory);
	}

	@TestFactory
	@Order(1)
	Collection<DynamicTest> testCase() throws IOException {
		URL url = getClass().getClassLoader().getResource("input");
		File dir = new File(url.getPath());
		Collection<DynamicTest> dynamicTests = new ArrayList<>();
		FilenameFilter xmlFilter = (dir1, name) -> name.endsWith(".xml");

		File[] files = dir.listFiles(xmlFilter);
		if (files != null) {
			for (File inputXML : files) {
				String testCaseName = inputXML.getName();

				String xmlInput = new String(Files.readAllBytes(inputXML.toPath()), StandardCharsets.UTF_8);

				HttpEntity<String> entity = new HttpEntity<>(xmlInput, headers);
				Executable exec = () -> {
					ResponseEntity<String> response = restTemplate.exchange(getUrl(urlEndpoint), HttpMethod.POST,
							entity, String.class);

					assertNotNull(response.getBody());

					String expectedFile = "expected/" + inputXML.getName().replace(".xml", ".json");
					InputStream expectedContent = getClass().getClassLoader().getResourceAsStream(expectedFile);
					assertNotNull(expectedContent, "Expected file not found: " + expectedFile);

					String expectedJson = IOUtils.toString(expectedContent, StandardCharsets.UTF_8);

					// Print debugging information
					System.out.println("Response received: " + response.getBody());
					System.out.println("Expected JSON: " + expectedJson);

					// Parse and compare JSON
					ObjectMapper objectMapper = new ObjectMapper();
					JsonNode actualJsonNode = objectMapper.readTree(response.getBody());
					JsonNode expectedJsonNode = objectMapper.readTree(expectedJson);

					String differences = "";
					if (!actualJsonNode.equals(expectedJsonNode)) {
						differences = "Differences found:\n" + "Actual: " + actualJsonNode.toPrettyString() + "\n"
								+ "Expected: " + expectedJsonNode.toPrettyString();
					}

					// Write output files for debugging
					Files.write(Paths.get(outputFolderPath + "/" + testCaseName + ".txt"), differences.getBytes());
					Files.write(Paths.get(outputFolderPath + "/" + testCaseName + ".json"),
							response.getBody().getBytes());

					// Assert equality
					assertEquals(expectedJsonNode, actualJsonNode,
							"The JSON response does not match the expected output.");
				};

				String testName = "Test Case: " + testCaseName;
				DynamicTest dTest = DynamicTest.dynamicTest(testName, exec);
				dynamicTests.add(dTest);
			}
		}
		return dynamicTests;
	}
}
