package code.shubham.app.backgroundverification.web.v1.controllers;

import code.shubham.test.AbstractSpringBootMVCTest;
import code.shubham.commons.TestCommonConstants;
import code.shubham.test.TestKafkaConsumer;
import code.shubham.commons.contexts.RoleContextHolder;
import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.commons.models.Event;
import code.shubham.commons.utils.JsonUtils;
import code.shubham.app.TestAppConstants;
import code.shubham.app.backgroundverification.dao.entities.BackgroundVerification;
import code.shubham.app.backgroundverification.dao.entities.BackgroundVerificationStatus;
import code.shubham.app.backgroundverification.dao.repositories.BackgroundVerificationRepository;
import code.shubham.app.backgroundverificatonmodels.UpdateBackgroundVerificationStatusRequest;
import code.shubham.app.constants.EventName;
import code.shubham.app.constants.EventType;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BackgroundVerificationControllerTest extends AbstractSpringBootMVCTest {

	private final String baseURL = "/v1/backgroundVerification";

	@Value("${background.verification.kafka.topic.name}")
	private String topicName;

	@Autowired
	private BackgroundVerificationRepository repository;

	private TestKafkaConsumer kafkaConsumer;

	@PostConstruct
	public void allSetUp() {
		this.kafkaConsumer = new TestKafkaConsumer(this.topicName);
	}

	@PreDestroy
	public void tearDownAll() {
		this.kafkaConsumer.destroy();
	}

	@BeforeEach
	public void setUp() {
		super.setUp();
		truncate("background_verification");
		this.kafkaConsumer.purge(this.topicName);
	}

	@AfterEach
	void tearDown() {
		RoleContextHolder.clear();
		this.kafkaConsumer.purge(this.topicName);
	}

	@Test
	void updateStatus_forbidden() throws Exception {
		final UpdateBackgroundVerificationStatusRequest request = UpdateBackgroundVerificationStatusRequest.builder()
			.clientReferenceId(TestAppConstants.BACKGROUND_VERIFICATION_CLIENT_REFERENCE_ID)
			.completedStatus("ONGOING")
			.build();

		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/updateStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().isForbidden());
	}

	@Test
	void updateStatus_with_invalid_fields_in_request() throws Exception {
		final UpdateBackgroundVerificationStatusRequest request = UpdateBackgroundVerificationStatusRequest.builder()
			.clientReferenceId("")
			.completedStatus("")
			.build();

		RoleContextHolder.set(Set.of("ADMIN"));
		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/updateStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is(400))
			.andExpect(content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n"
					+ "    \"error\": [\n" + "        {\n" + "            \"clientReferenceId\": [\n"
					+ "                \"clientReferenceId must not be empty.\"\n" + "            ],\n"
					+ "            \"completedStatus\": [\n"
					+ "                \"completedStatus must not be empty.\",\n"
					+ "                \"invalid value for completedStatus: \"\n" + "            ]\n" + "        }\n"
					+ "    ]\n" + "}"));
	}

	@Test
	void updateStatus_background_verification_not_initiated() throws Exception {
		RoleContextHolder.set(Set.of("ADMIN"));

		final UpdateBackgroundVerificationStatusRequest request = UpdateBackgroundVerificationStatusRequest.builder()
			.clientReferenceId(TestAppConstants.BACKGROUND_VERIFICATION_CLIENT_REFERENCE_ID)
			.completedStatus("ONGOING")
			.build();
		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/updateStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is4xxClientError())
			.andExpect(content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n"
					+ "    \"error\": [\n" + "        {\n" + "            \"clientReferenceId\": [\n"
					+ "                \"No background verification initiated with clientReferenceId: "
					+ request.getClientReferenceId() + "\"\n" + "            ]\n" + "        }\n" + "    ]\n" + "}"));
	}

	@Test
	void updateStatus_already_completed() throws Exception {
		this.repository.save(BackgroundVerification.builder()
			.userId(TestCommonConstants.USER_ID)
			.applicantId(TestAppConstants.DRIVER_ID)
			.applicantType(TestAppConstants.APPLICANT_TYPE_DRIVER)
			.clientReferenceId(TestAppConstants.BACKGROUND_VERIFICATION_CLIENT_REFERENCE_ID)
			.status(BackgroundVerificationStatus.COMPLETED)
			.build());
		RoleContextHolder.set(Set.of("ADMIN"));

		final UpdateBackgroundVerificationStatusRequest request = UpdateBackgroundVerificationStatusRequest.builder()
			.clientReferenceId(TestAppConstants.BACKGROUND_VERIFICATION_CLIENT_REFERENCE_ID)
			.completedStatus("ONGOING")
			.build();
		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/updateStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is4xxClientError())
			.andExpect(content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n"
					+ "    \"error\": [\n" + "        {\n" + "            \"clientReferenceId\": [\n"
					+ "                \"background verification completed for clientReferenceId: "
					+ request.getClientReferenceId() + "\"\n" + "            ]\n" + "        }\n" + "    ]\n" + "}"));
	}

	@Test
	void updateStatus_success() throws Exception {
		final BackgroundVerification backgroundVerification = this.repository.save(BackgroundVerification.builder()
			.userId(TestCommonConstants.USER_ID)
			.applicantId(TestAppConstants.DRIVER_ID)
			.applicantType(TestAppConstants.APPLICANT_TYPE_DRIVER)
			.clientReferenceId(TestAppConstants.BACKGROUND_VERIFICATION_CLIENT_REFERENCE_ID)
			.status(BackgroundVerificationStatus.ONGOING)
			.build());
		RoleContextHolder.set(Set.of("ADMIN"));

		final UpdateBackgroundVerificationStatusRequest request = UpdateBackgroundVerificationStatusRequest.builder()
			.clientReferenceId(TestAppConstants.BACKGROUND_VERIFICATION_CLIENT_REFERENCE_ID)
			.completedStatus("ONGOING")
			.build();
		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/updateStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().isOk());

		final var updated = this.repository
			.findByClientReferenceId(TestAppConstants.BACKGROUND_VERIFICATION_CLIENT_REFERENCE_ID);
		Assertions.assertTrue(updated.isPresent());
		Assertions.assertEquals(updated.get().getStatus().name(), BackgroundVerificationStatus.COMPLETED.name());

		final Event event = this.kafkaConsumer.poll(1).get(0);
		Assertions.assertEquals(event.getUserId(), TestCommonConstants.USER_ID);
		Assertions.assertNotNull(event.getCreatedAt());
		Assertions.assertNotNull(event.getCorrelationId());
		Assertions.assertEquals(event.getEventName(), EventName.BackgroundVerificationStatusUpdated.name());
		Assertions.assertEquals(event.getEventType(), EventType.BACKGROUND_VERIFICATION.name());
		BackgroundVerification backgroundVerificationFromEvent = JsonUtils.as(event.getData(),
				BackgroundVerification.class);
		Assertions.assertEquals(backgroundVerificationFromEvent.getStatus().name(), updated.get().getStatus().name());
	}

	@Test
	void get_without_userId_request_param() throws Exception {
		UserIDContextHolder.set(TestCommonConstants.USER_ID);
		this.mockMvc.perform(MockMvcRequestBuilders.get(this.baseURL + "/all").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(400))
			.andExpect(content().json("{\n" + "    \"type\": \"about:blank\",\n" + "    \"title\": \"Bad Request\",\n"
					+ "    \"status\": 400,\n" + "    \"detail\": \"Required parameter 'userId' is not present.\",\n"
					+ "    \"instance\": \"/v1/backgroundVerification/all\"\n" + "}"));
	}

	@Test
	void get_all_Success() throws Exception {
		UserIDContextHolder.set(TestCommonConstants.USER_ID);
		RoleContextHolder.set(Set.of("ADMIN"));
		this.repository.save(BackgroundVerification.builder()
			.status(BackgroundVerificationStatus.COMPLETED)
			.userId(TestCommonConstants.USER_ID)
			.applicantId(TestAppConstants.DRIVER_ID)
			.applicantType(TestAppConstants.APPLICANT_TYPE_DRIVER)
			.clientReferenceId(TestAppConstants.BACKGROUND_VERIFICATION_CLIENT_REFERENCE_ID)
			.build());
		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.baseURL + "/all")
				.param("userId", TestCommonConstants.USER_ID)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(302))
			.andExpect(content().json("{\n" + "    \"statusCode\": 302,\n" + "    \"data\": [\n" + "        {\n"
					+ "            \"applicantId\": \"" + TestAppConstants.DRIVER_ID + "\",\n"
					+ "            \"applicantType\": \"DRIVER\",\n" + "            \"status\": \"COMPLETED\",\n"
					+ "            \"userId\": \"" + TestCommonConstants.USER_ID + "\",\n"
					+ "            \"clientReferenceId\": \""
					+ TestAppConstants.BACKGROUND_VERIFICATION_CLIENT_REFERENCE_ID + "\"\n" + "        }\n" + "    ],\n"
					+ "    \"error\": null\n" + "}"));
	}

}