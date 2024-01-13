package code.shubham.craft.driveronboard.web.v1.controllers;

import code.shubham.commons.AbstractSpringBootMVCTest;
import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.TestKafkaConsumer;
import code.shubham.commons.contexts.RoleContextHolder;
import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.commons.models.Event;
import code.shubham.commons.utils.JsonUtils;
import code.shubham.craft.CraftTestConstants;
import code.shubham.craft.backgroundverification.dao.entities.BackgroundVerification;
import code.shubham.craft.backgroundverification.dao.entities.BackgroundVerificationStatus;
import code.shubham.craft.constants.EventName;
import code.shubham.craft.constants.EventType;
import code.shubham.craft.documentstore.dao.entities.Document;
import code.shubham.craft.documentstore.dao.repositories.DocumentRepository;
import code.shubham.craft.drivermodels.CompleteOnboardStageRequest;
import code.shubham.craft.driveronboard.dao.entities.DriverOnboard;
import code.shubham.craft.driveronboard.dao.entities.DriverOnboardStatus;
import code.shubham.craft.driveronboard.dao.repositories.DriverOnboardRepository;
import code.shubham.craft.driveronboardmodels.DriverOnboardStatusUpdatedEventData;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DriverOnboardControllerTest extends AbstractSpringBootMVCTest {

	@Value("${driver.onboard.kafka.topic.name}")
	private String topicName;

	private final String baseURL = "/v1/drivers/onboard";

	@Autowired
	private DriverOnboardRepository repository;

	@Autowired
	private DocumentRepository documentRepository;

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
		truncate("documents");
		this.documentRepository.save(Document.builder()
			.blobId(TestCommonConstants.BLOB_ID)
			.owner(TestCommonConstants.USER_ID)
			.name("test")
			.build());
		truncate("driver_onboard");
		this.kafkaConsumer.purge(this.topicName);
	}

	@AfterEach
	void tearDown() {
		truncate("driver_onboard");
		truncate("documents");
		this.kafkaConsumer.purge(this.topicName);
	}

	@Test
	void updateStatus_user_not_allowed() throws Exception {
		final DriverOnboard created = this.repository.save(DriverOnboard.builder()
			.driverId(CraftTestConstants.DRIVER_ID)
			.status(DriverOnboardStatus.BACKGROUND_VERIFICATION)
			.userId(TestCommonConstants.USER_ID)
			.clientReferenceId(CraftTestConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
			.build());
		UserIDContextHolder.set("INVALID_USER");

		final CompleteOnboardStageRequest request = CompleteOnboardStageRequest.builder()
			.driverOnboardId(created.getId())
			.completedOnboardStatus(DriverOnboardStatus.DOCUMENT_COLLECTION.name())
			.userId(TestCommonConstants.USER_ID)
			.build();
		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/updateStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is4xxClientError())
			.andExpect(
					content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n" + "    \"error\": [\n"
							+ "        {\n" + "            \"userId\": [\n" + "                \"User with userId: "
							+ TestCommonConstants.USER_ID + " not allowed to perform the operation\"\n"
							+ "            ]\n" + "        }\n" + "    ]\n" + "}"));
	}

	@Test
	void updateStatus_without_existing_driver_onboard_state() throws Exception {
		UserIDContextHolder.set(TestCommonConstants.USER_ID);

		final CompleteOnboardStageRequest request = CompleteOnboardStageRequest.builder()
			.driverOnboardId(CraftTestConstants.DRIVER_ONBOARD_ID)
			.completedOnboardStatus(DriverOnboardStatus.DOCUMENT_COLLECTION.name())
			.userId(TestCommonConstants.USER_ID)
			.build();
		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/updateStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is4xxClientError())
			.andExpect(content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n"
					+ "    \"error\": [\n" + "        {\n" + "            \"driverOnboardId\": [\n"
					+ "                \"No onboarding state for driverOnboardId: "
					+ CraftTestConstants.DRIVER_ONBOARD_ID + "\"\n" + "            ]\n" + "        }\n" + "    ]\n"
					+ "}"));
	}

	@Test
	void updateStatus_with_invalid_driver_onboard_status() throws Exception {
		final DriverOnboard created = this.repository.save(DriverOnboard.builder()
			.driverId(CraftTestConstants.DRIVER_ID)
			.status(DriverOnboardStatus.BACKGROUND_VERIFICATION)
			.userId(TestCommonConstants.USER_ID)
			.clientReferenceId(CraftTestConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
			.build());
		UserIDContextHolder.set(TestCommonConstants.USER_ID);

		final CompleteOnboardStageRequest request = CompleteOnboardStageRequest.builder()
			.driverOnboardId(created.getId())
			.completedOnboardStatus(DriverOnboardStatus.DOCUMENT_COLLECTION.name())
			.userId(TestCommonConstants.USER_ID)
			.build();
		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/updateStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is4xxClientError())
			.andExpect(content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n"
					+ "    \"error\": [\n" + "        {\n" + "            \"status\": [\n"
					+ "                \"Invalid onboard status in request: DOCUMENT_COLLECTION\"\n" + "            ]\n"
					+ "        }\n" + "    ]\n" + "}"));
	}

	@Test
	void updateStatus_DOCUMENT_COLLECTION_without_uploaded_document() throws Exception {
		truncate("documents");
		final DriverOnboard created = this.repository.save(DriverOnboard.builder()
			.driverId(CraftTestConstants.DRIVER_ID)
			.status(DriverOnboardStatus.DOCUMENT_COLLECTION)
			.userId(TestCommonConstants.USER_ID)
			.clientReferenceId(CraftTestConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
			.build());
		UserIDContextHolder.set(TestCommonConstants.USER_ID);

		final CompleteOnboardStageRequest request = CompleteOnboardStageRequest.builder()
			.driverOnboardId(created.getId())
			.completedOnboardStatus(DriverOnboardStatus.DOCUMENT_COLLECTION.name())
			.userId(TestCommonConstants.USER_ID)
			.build();
		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/updateStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is4xxClientError())
			.andExpect(content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n"
					+ "    \"error\": [\n" + "        {\n" + "            \"driverId\": [\n"
					+ "                \"no documents uploaded by driver: " + CraftTestConstants.DRIVER_ID + "\"\n"
					+ "            ]\n" + "        }\n" + "    ]\n" + "}"));
	}

	@Test
	void updateStatus_BACKGROUND_VERIFICATION_unauthorized() throws Exception {
		final DriverOnboard created = this.repository.save(DriverOnboard.builder()
			.driverId(CraftTestConstants.DRIVER_ID)
			.status(DriverOnboardStatus.BACKGROUND_VERIFICATION)
			.userId(TestCommonConstants.USER_ID)
			.clientReferenceId(CraftTestConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
			.build());
		UserIDContextHolder.set(TestCommonConstants.USER_ID);

		final CompleteOnboardStageRequest request = CompleteOnboardStageRequest.builder()
			.driverOnboardId(created.getId())
			.completedOnboardStatus(DriverOnboardStatus.BACKGROUND_VERIFICATION.name())
			.userId(TestCommonConstants.USER_ID)
			.build();
		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/updateStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is4xxClientError())
			.andExpect(content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n"
					+ "    \"error\": [\n" + "        {\n" + "            \"completedStatus\": [\n"
					+ "                \"BACKGROUND_VERIFICATION is admin updatable status\"\n" + "            ]\n"
					+ "        }\n" + "    ]\n" + "}"));
	}

	@Test
	void getAll_without_userId_request_param() throws Exception {
		final DriverOnboard created = this.repository.save(DriverOnboard.builder()
			.driverId(CraftTestConstants.DRIVER_ID)
			.status(DriverOnboardStatus.BACKGROUND_VERIFICATION)
			.userId(TestCommonConstants.USER_ID)
			.clientReferenceId(CraftTestConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
			.build());
		UserIDContextHolder.set(TestCommonConstants.USER_ID);
		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.baseURL + "/all")
				.param("driverId", CraftTestConstants.DRIVER_ID)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(400))
			.andExpect(content().json("{\n" + "    \"type\": \"about:blank\",\n" + "    \"title\": \"Bad Request\",\n"
					+ "    \"status\": 400,\n" + "    \"detail\": \"Required parameter 'userId' is not present.\",\n"
					+ "    \"instance\": \"/v1/drivers/onboard/all\"\n" + "}"));
	}

	@Test
	void getAll_without_driverId_request_param() throws Exception {
		final DriverOnboard created = this.repository.save(DriverOnboard.builder()
			.driverId(CraftTestConstants.DRIVER_ID)
			.status(DriverOnboardStatus.BACKGROUND_VERIFICATION)
			.userId(TestCommonConstants.USER_ID)
			.clientReferenceId(CraftTestConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
			.build());
		UserIDContextHolder.set(TestCommonConstants.USER_ID);
		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.baseURL + "/all")
				.param("userId", TestCommonConstants.USER_ID)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(400))
			.andExpect(content().json("{\n" + "    \"type\": \"about:blank\",\n" + "    \"title\": \"Bad Request\",\n"
					+ "    \"status\": 400,\n" + "    \"detail\": \"Required parameter 'driverId' is not present.\",\n"
					+ "    \"instance\": \"/v1/drivers/onboard/all\"\n" + "}"));
	}

	@Test
	void getAll_Success() throws Exception {
		final DriverOnboard created = this.repository.save(DriverOnboard.builder()
			.driverId(CraftTestConstants.DRIVER_ID)
			.status(DriverOnboardStatus.BACKGROUND_VERIFICATION)
			.userId(TestCommonConstants.USER_ID)
			.clientReferenceId(CraftTestConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
			.build());
		UserIDContextHolder.set(TestCommonConstants.USER_ID);
		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.baseURL + "/all")
				.param("driverId", CraftTestConstants.DRIVER_ID)
				.param("userId", TestCommonConstants.USER_ID)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(302))
			.andExpect(content().json("{\n" + "    \"statusCode\": 302,\n" + "    \"data\": {\n"
					+ "        \"onboards\": [\n" + "            {\n" + "                \"driverOnboardId\": "
					+ created.getId() + ",\n" + "                \"driverId\": " + CraftTestConstants.DRIVER_ID + ",\n"
					+ "                \"status\": \"BACKGROUND_VERIFICATION\",\n" + "                \"userId\": "
					+ TestCommonConstants.USER_ID + "\n" + "            }\n" + "        ]\n" + "    },\n"
					+ "    \"error\": null\n" + "}"));
	}

	@Test
	void updateStatus_DOCUMENT_COLLECTION_Success() throws Exception {
		final DriverOnboard created = this.repository.save(DriverOnboard.builder()
			.driverId(CraftTestConstants.DRIVER_ID)
			.status(DriverOnboardStatus.DOCUMENT_COLLECTION)
			.userId(TestCommonConstants.USER_ID)
			.clientReferenceId(CraftTestConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
			.build());
		UserIDContextHolder.set(TestCommonConstants.USER_ID);

		final CompleteOnboardStageRequest request = CompleteOnboardStageRequest.builder()
			.driverOnboardId(created.getId())
			.completedOnboardStatus(DriverOnboardStatus.DOCUMENT_COLLECTION.name())
			.userId(TestCommonConstants.USER_ID)
			.build();

		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/updateStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().isOk())
			.andExpect(content().json("{\n" + "    \"statusCode\": 200,\n" + "    \"data\": {\n"
					+ "        \"status\": \"BACKGROUND_VERIFICATION\",\n" + "        \"driverId\": "
					+ CraftTestConstants.DRIVER_ID + ",\n" + "        \"userId\": " + TestCommonConstants.USER_ID
					+ ",\n" + "        \"clientReferenceId\": " + CraftTestConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID
					+ "\n" + "    },\n" + "    \"error\": null\n" + "}"));

		final var updated = this.repository.findByDriverId(CraftTestConstants.DRIVER_ID);
		Assertions.assertTrue(updated.isPresent());
		Assertions.assertEquals(updated.get().getStatus().name(), DriverOnboardStatus.BACKGROUND_VERIFICATION.name());

		final Event event = this.kafkaConsumer.poll(1).get(0);
		Assertions.assertEquals(event.getUserId(), TestCommonConstants.USER_ID);
		Assertions.assertNotNull(event.getCreatedAt());
		Assertions.assertNotNull(event.getCorrelationId());
		Assertions.assertEquals(event.getEventName(), EventName.DriverOnboardStatusUpdated.name());
		Assertions.assertEquals(event.getEventType(), EventType.DRIVER_ONBOARD.name());
		DriverOnboardStatusUpdatedEventData data = JsonUtils.as(event.getData(),
				DriverOnboardStatusUpdatedEventData.class);
		Assertions.assertEquals(data.getDriverOnboard().getStatus(), updated.get().getStatus().name());
	}

	@Test
	void updateStatus_BACKGROUND_VERIFICATION_Success() throws Exception {
		final DriverOnboard created = this.repository.save(DriverOnboard.builder()
			.driverId(CraftTestConstants.DRIVER_ID)
			.status(DriverOnboardStatus.BACKGROUND_VERIFICATION)
			.userId(TestCommonConstants.USER_ID)
			.clientReferenceId(CraftTestConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
			.build());
		UserIDContextHolder.set(TestCommonConstants.USER_ID);
		RoleContextHolder.set(Set.of("ADMIN"));

		final CompleteOnboardStageRequest request = CompleteOnboardStageRequest.builder()
			.driverOnboardId(created.getId())
			.completedOnboardStatus(DriverOnboardStatus.BACKGROUND_VERIFICATION.name())
			.userId(TestCommonConstants.USER_ID)
			.build();
		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/updateStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().isOk())
			.andExpect(content().json("{\n" + "    \"statusCode\": 200,\n" + "    \"data\": {\n"
					+ "        \"status\": \"SHIPPING_OF_TRACKING_DEVICE\",\n" + "        \"driverId\": "
					+ CraftTestConstants.DRIVER_ID + ",\n" + "        \"userId\": " + TestCommonConstants.USER_ID
					+ ",\n" + "        \"clientReferenceId\": " + CraftTestConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID
					+ "\n" + "    },\n" + "    \"error\": null\n" + "}"));

		final var updated = this.repository.findByDriverId(CraftTestConstants.DRIVER_ID);
		Assertions.assertTrue(updated.isPresent());
		Assertions.assertEquals(DriverOnboardStatus.SHIPPING_OF_TRACKING_DEVICE.name(),
				updated.get().getStatus().name());

		final Event event = this.kafkaConsumer.poll(1).get(0);
		Assertions.assertEquals(event.getUserId(), TestCommonConstants.USER_ID);
		Assertions.assertNotNull(event.getCreatedAt());
		Assertions.assertNotNull(event.getCorrelationId());
		Assertions.assertEquals(event.getEventName(), EventName.DriverOnboardStatusUpdated.name());
		Assertions.assertEquals(event.getEventType(), EventType.DRIVER_ONBOARD.name());
		DriverOnboardStatusUpdatedEventData data = JsonUtils.as(event.getData(),
				DriverOnboardStatusUpdatedEventData.class);
		Assertions.assertEquals(data.getDriverOnboard().getStatus(), updated.get().getStatus().name());
	}

}