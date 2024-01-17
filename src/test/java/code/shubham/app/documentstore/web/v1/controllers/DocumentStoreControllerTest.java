package code.shubham.app.documentstore.web.v1.controllers;

import code.shubham.test.AbstractSpringBootMVCTest;
import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.core.blobstore.dao.entities.Blob;
import code.shubham.core.blobstore.dao.repositories.BlobRepository;
import code.shubham.app.documentstore.dao.entities.Document;
import code.shubham.app.documentstore.dao.repositories.DocumentRepository;
import code.shubham.app.documentstoremodels.SaveDocumentRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DocumentStoreControllerTest extends AbstractSpringBootMVCTest {

	private final String baseURL = "/v1/documents";

	@Autowired
	private DocumentRepository repository;

	@Autowired
	private BlobRepository blobRepository;

	@BeforeEach
	public void setUp() {
		super.setUp();
		truncate("documents");
		UserIDContextHolder.set(TestCommonConstants.USER_ID);
	}

	@AfterEach
	void tearDown() {
		truncate("documents");
	}

	@Test
	void getDocumentUploadURL() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.baseURL + "/uploadURL")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(new HashMap<>())))
			.andExpect(status().is2xxSuccessful());
	}

	@Test
	void save_with_invalid_request_fields() throws Exception {
		final SaveDocumentRequest request = SaveDocumentRequest.builder().blobId("").name("").userId("").build();

		this.mockMvc.perform(
				MockMvcRequestBuilders.post(this.baseURL).contentType(MediaType.APPLICATION_JSON).content(as(request)))
			.andExpect(status().is(400))
			.andExpect(content().json(
					"{\"statusCode\":400,\"data\":null,\"error\":[{\"name\":[\"name must not be empty.\"],\"blobId\":[\"blobId must not be empty.\"],\"userId\":[\"userId must not be empty.\"]}]}"));

		Assertions.assertFalse(this.repository.findByOwnerAndName("", "").isPresent());
	}

	@Test
	void save_with_invalid_user_id() throws Exception {
		final SaveDocumentRequest request = SaveDocumentRequest.builder()
			.blobId(TestCommonConstants.BLOB_ID)
			.name("test")
			.userId("INVALID_USER_ID")
			.build();
		this.mockMvc.perform(
				MockMvcRequestBuilders.post(this.baseURL).contentType(MediaType.APPLICATION_JSON).content(as(request)))
			.andExpect(status().is(400))
			.andExpect(content().json(
					"{\"statusCode\":400,\"data\":null,\"error\":[{\"userId\":[\"User with userId: INVALID_USER_ID not allowed to perform the operation\"]}]}"));

		Assertions.assertFalse(this.repository.findByOwnerAndName("INVALID_USER_ID", "test").isPresent());
	}

	@Test
	void save_without_existing_blob() throws Exception {
		final SaveDocumentRequest request = SaveDocumentRequest.builder()
			.blobId(TestCommonConstants.BLOB_ID)
			.name("test")
			.userId(TestCommonConstants.USER_ID)
			.build();

		this.mockMvc.perform(
				MockMvcRequestBuilders.post(this.baseURL).contentType(MediaType.APPLICATION_JSON).content(as(request)))
			.andExpect(status().is(400))
			.andExpect(content()
				.json("{\"statusCode\":400,\"data\":null,\"error\":[{\"blobId\":[\"blob does not exist\"]}]}"));

		Assertions.assertFalse(this.repository.findByOwnerAndName(TestCommonConstants.USER_ID, "test").isPresent());
	}

	@Test
	void save_Success() throws Exception {
		final Blob existingBlob = this.blobRepository.save(Blob.builder()
			.bucket("test")
			.keyName(TestCommonConstants.USER_ID)
			.owner(TestCommonConstants.USER_ID)
			.build());
		final SaveDocumentRequest request = SaveDocumentRequest.builder()
			.blobId(existingBlob.getId())
			.name("test")
			.userId(TestCommonConstants.USER_ID)
			.build();

		this.mockMvc.perform(
				MockMvcRequestBuilders.post(this.baseURL).contentType(MediaType.APPLICATION_JSON).content(as(request)))
			.andExpect(status().is(201))
			.andExpect(content()
				.json("{\n" + "    \"statusCode\": 201,\n" + "    \"data\": {\n" + "        \"name\": \"test\",\n"
						+ "        \"owner\": \"" + TestCommonConstants.USER_ID + "\",\n" + "        \"blobId\": \""
						+ existingBlob.getId() + "\"\n" + "    },\n" + "    \"error\": null\n" + "}"));

		Assertions.assertTrue(this.repository.findByOwnerAndName(TestCommonConstants.USER_ID, "test").isPresent());
	}

	@Test
	void save_Success_with_existing_document() throws Exception {
		final Blob existingBlob = this.blobRepository.save(Blob.builder()
			.bucket("test")
			.keyName(TestCommonConstants.USER_ID)
			.owner(TestCommonConstants.USER_ID)
			.build());
		this.repository.save(Document.builder()
			.name("test")
			.owner(TestCommonConstants.USER_ID)
			.blobId(existingBlob.getId())
			.build());
		final SaveDocumentRequest request = SaveDocumentRequest.builder()
			.blobId(existingBlob.getId())
			.name("test")
			.userId(TestCommonConstants.USER_ID)
			.build();

		this.mockMvc.perform(
				MockMvcRequestBuilders.post(this.baseURL).contentType(MediaType.APPLICATION_JSON).content(as(request)))
			.andExpect(status().is(201))
			.andExpect(content()
				.json("{\n" + "    \"statusCode\": 201,\n" + "    \"data\": {\n" + "        \"name\": \"test\",\n"
						+ "        \"owner\": \"" + TestCommonConstants.USER_ID + "\",\n" + "        \"blobId\": \""
						+ existingBlob.getId() + "\"\n" + "    },\n" + "    \"error\": null\n" + "}"));

		Assertions.assertTrue(this.repository.findByOwnerAndName(TestCommonConstants.USER_ID, "test").isPresent());
	}

}