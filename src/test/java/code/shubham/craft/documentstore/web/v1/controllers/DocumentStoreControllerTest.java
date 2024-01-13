package code.shubham.craft.documentstore.web.v1.controllers;

import code.shubham.commons.AbstractSpringBootMVCTest;
import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.contexts.UserIDContextHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DocumentStoreControllerTest extends AbstractSpringBootMVCTest {

	private final String baseURL = "/v1/documents";

	@BeforeEach
	public void setUp() {
		super.setUp();
		truncate("documents");
	}

	@AfterEach
	void tearDown() {
		truncate("documents");
	}

	@Test
	void getDocumentUploadURL() throws Exception {
		UserIDContextHolder.set(TestCommonConstants.USER_ID);
		final Map<String, String> request = new HashMap<>();

		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.baseURL + "/uploadURL")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is2xxSuccessful());
	}

}