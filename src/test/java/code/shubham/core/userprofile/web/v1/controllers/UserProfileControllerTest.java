package code.shubham.core.userprofile.web.v1.controllers;

import code.shubham.commons.AbstractSpringBootMVCTest;
import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.core.userprofile.dao.entities.UserProfile;
import code.shubham.core.userprofile.dao.repositories.UserProfileRepository;
import code.shubham.core.userprofilemodels.UpdateUserProfileRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserProfileControllerTest extends AbstractSpringBootMVCTest {

	private final String baseURL = "/v1/users/profiles";

	@Autowired
	private UserProfileRepository repository;

	@BeforeEach
	public void setUp() {
		super.setUp();
		truncate("user_profiles");
		UserIDContextHolder.set(TestCommonConstants.USER_ID);
	}

	@AfterEach
	void tearDown() {
		truncate("user_profiles");
	}

	@Test
	void update_with_invalid_params_request() throws Exception {
		final UpdateUserProfileRequest request = new UpdateUserProfileRequest();
		request.setAddress("");

		this.mockMvc
			.perform(MockMvcRequestBuilders.put(this.baseURL + "/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is(400))
			.andExpect(content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n"
					+ "    \"error\": [\n" + "        {\n" + "            \"address\": [\n"
					+ "                \"address must not be empty.\"\n" + "            ]\n" + "        }\n" + "    ]\n"
					+ "}"));
	}

	@Test
	void update_Success() throws Exception {
		final UpdateUserProfileRequest request = new UpdateUserProfileRequest();
		request.setAddress("address");

		this.mockMvc
			.perform(MockMvcRequestBuilders.put(this.baseURL + "/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is(200))
			.andExpect(content().json("{\n" + "    \"statusCode\": 200,\n" + "    \"data\": {\n"
					+ "        \"userId\": \"" + TestCommonConstants.USER_ID + "\",\n"
					+ "        \"address\": \"address\"\n" + "    },\n" + "    \"error\": null\n" + "}"));

		final var profiles = this.repository.findByUserId(TestCommonConstants.USER_ID);

		Assertions.assertTrue(profiles.isPresent());
		Assertions.assertEquals(TestCommonConstants.USER_ID, profiles.get().getUserId());
		Assertions.assertEquals("address", profiles.get().getAddress());
	}

	@Test
	void update_Success_with_existing_profile() throws Exception {
		this.repository.save(UserProfile.builder().userId(TestCommonConstants.USER_ID).build());
		final UpdateUserProfileRequest request = new UpdateUserProfileRequest();
		request.setAddress("address");

		this.mockMvc
			.perform(MockMvcRequestBuilders.put(this.baseURL + "/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is(200))
			.andExpect(content().json("{\n" + "    \"statusCode\": 200,\n" + "    \"data\": {\n"
					+ "        \"userId\": \"" + TestCommonConstants.USER_ID + "\",\n"
					+ "        \"address\": \"address\"\n" + "    },\n" + "    \"error\": null\n" + "}"));

		final var profiles = this.repository.findByUserId(TestCommonConstants.USER_ID);

		Assertions.assertTrue(profiles.isPresent());
		Assertions.assertEquals(TestCommonConstants.USER_ID, profiles.get().getUserId());
		Assertions.assertEquals("address", profiles.get().getAddress());
	}

	@Test
	void getByUserId_without_existing_user() throws Exception {
		final UpdateUserProfileRequest request = new UpdateUserProfileRequest();
		request.setAddress("address");

		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.baseURL)
				.contentType(MediaType.APPLICATION_JSON)
				.param("userId", TestCommonConstants.USER_ID)
				.content(as(request)))
			.andExpect(status().is(400))
			.andExpect(content()
				.json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n" + "    \"error\": [\n"
						+ "        {\n" + "            \"userId\": [\n" + "                \"No user found with id: "
						+ TestCommonConstants.USER_ID + "\"\n" + "            ]\n" + "        }\n" + "    ]\n" + "}"));

		final var profiles = this.repository.findByUserId(TestCommonConstants.USER_ID);

		Assertions.assertFalse(profiles.isPresent());
	}

	@Test
	void getByUserId_with_invalid_user() throws Exception {
		final UpdateUserProfileRequest request = new UpdateUserProfileRequest();
		request.setAddress("address");

		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.baseURL)
				.contentType(MediaType.APPLICATION_JSON)
				.param("userId", "INVALID_USER_ID")
				.content(as(request)))
			.andExpect(status().is(400))
			.andExpect(content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n"
					+ "    \"error\": [\n" + "        {\n" + "            \"userId\": [\n"
					+ "                \"User with userId: INVALID_USER_ID not allowed to perform the operation\"\n"
					+ "            ]\n" + "        }\n" + "    ]\n" + "}"));

		final var profiles = this.repository.findByUserId(TestCommonConstants.USER_ID);

		Assertions.assertFalse(profiles.isPresent());
	}

	@Test
	void getByUserId_Success() throws Exception {
		this.repository.save(UserProfile.builder().userId(TestCommonConstants.USER_ID).address("address").build());
		final UpdateUserProfileRequest request = new UpdateUserProfileRequest();
		request.setAddress("address");

		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.baseURL)
				.contentType(MediaType.APPLICATION_JSON)
				.param("userId", TestCommonConstants.USER_ID)
				.content(as(request)))
			.andExpect(status().is(200))
			.andExpect(content().json("{\n" + "    \"statusCode\": 200,\n" + "    \"data\": {\n"
					+ "        \"userId\": \"" + TestCommonConstants.USER_ID + "\",\n"
					+ "        \"address\": \"address\"\n" + "    },\n" + "    \"error\": null\n" + "}"));

		final var profiles = this.repository.findByUserId(TestCommonConstants.USER_ID);

		Assertions.assertTrue(profiles.isPresent());
	}

}