package code.shubham.craft.backgroundverification.web.v1.controllers;

import code.shubham.commons.AbstractTest;
import code.shubham.commons.TestConstants;
import code.shubham.commons.contexts.RoleContextHolder;
import code.shubham.commons.models.Event;
import code.shubham.commons.utils.JsonUtils;
import code.shubham.craft.CraftTestConstants;
import code.shubham.craft.backgroundverification.dao.entities.BackgroundVerification;
import code.shubham.craft.backgroundverification.dao.entities.BackgroundVerificationStatus;
import code.shubham.craft.backgroundverification.dao.repositories.BackgroundVerificationRepository;
import code.shubham.craft.backgroundverificatonmodels.UpdateBackgroundVerificationStatusRequest;
import code.shubham.craft.constants.EventName;
import code.shubham.craft.constants.EventType;
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

class BackgroundVerificationControllerTest extends AbstractTest {

	private final String baseURL = "/backgroundVerification/v1";

	@Value("${background.verification.kafka.topic.name}")
	private String topicName;

	@Autowired
	private BackgroundVerificationRepository repository;

	@Override
	@BeforeEach
	public void setUp() {
		super.setUp();
		truncate("background_verification");
	}

	@AfterEach
	void tearDown() {
		RoleContextHolder.clear();
	}

	@Test
	void updateStatus_forbidden() throws Exception {
		final UpdateBackgroundVerificationStatusRequest request = UpdateBackgroundVerificationStatusRequest.builder()
			.clientReferenceId(CraftTestConstants.CLIENT_REFERENCE_ID)
			.completedStatus("ONGOING")
			.build();

		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/updateStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().isForbidden());
	}

	@Test
	void updateStatus_background_verification_not_initiated() throws Exception {
		RoleContextHolder.set(Set.of("ADMIN"));

		final UpdateBackgroundVerificationStatusRequest request = UpdateBackgroundVerificationStatusRequest.builder()
			.clientReferenceId(CraftTestConstants.CLIENT_REFERENCE_ID)
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
			.userId(TestConstants.USER_ID)
			.applicantId(CraftTestConstants.DRIVER_ID)
			.applicantType(CraftTestConstants.APPLICANT_TYPE_DRIVER)
			.clientReferenceId(CraftTestConstants.CLIENT_REFERENCE_ID)
			.status(BackgroundVerificationStatus.COMPLETED)
			.build());
		RoleContextHolder.set(Set.of("ADMIN"));

		final UpdateBackgroundVerificationStatusRequest request = UpdateBackgroundVerificationStatusRequest.builder()
			.clientReferenceId(CraftTestConstants.CLIENT_REFERENCE_ID)
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
			.userId(TestConstants.USER_ID)
			.applicantId(CraftTestConstants.DRIVER_ID)
			.applicantType(CraftTestConstants.APPLICANT_TYPE_DRIVER)
			.clientReferenceId(CraftTestConstants.CLIENT_REFERENCE_ID)
			.status(BackgroundVerificationStatus.ONGOING)
			.build());
		RoleContextHolder.set(Set.of("ADMIN"));

		final UpdateBackgroundVerificationStatusRequest request = UpdateBackgroundVerificationStatusRequest.builder()
			.clientReferenceId(CraftTestConstants.CLIENT_REFERENCE_ID)
			.completedStatus("ONGOING")
			.build();
		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/updateStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().isOk());

		final var updated = this.repository.findByClientReferenceId(CraftTestConstants.CLIENT_REFERENCE_ID);
		Assertions.assertTrue(updated.isPresent());
		Assertions.assertEquals(updated.get().getStatus().name(), BackgroundVerificationStatus.COMPLETED.name());

		final Event event = this.kafkaConsumer.poll(topicName, 1).get(0);
		Assertions.assertEquals(event.getUserId(), TestConstants.USER_ID);
		Assertions.assertNotNull(event.getCreatedAt());
		Assertions.assertNotNull(event.getCorrelationId());
		Assertions.assertEquals(event.getEventName(), EventName.BackgroundVerificationStatusUpdated.name());
		Assertions.assertEquals(event.getEventType(), EventType.BACKGROUND_VERIFICATION.name());
		BackgroundVerification backgroundVerificationFromEvent = JsonUtils.as(event.getData(),
				BackgroundVerification.class);
		Assertions.assertEquals(backgroundVerificationFromEvent.getStatus().name(), updated.get().getStatus().name());
	}

}