package code.shubham.craft.shipment.web.v1.controllers;

import code.shubham.commons.AbstractSpringBootMVCTest;
import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.TestKafkaConsumer;
import code.shubham.commons.contexts.RoleContextHolder;
import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.commons.models.Event;
import code.shubham.commons.utils.JsonUtils;
import code.shubham.craft.CraftTestConstants;
import code.shubham.craft.constants.EventName;
import code.shubham.craft.constants.EventType;
import code.shubham.craft.driveronboard.dao.entities.DriverOnboardStatus;
import code.shubham.craft.driveronboardmodels.DriverOnboardStatusUpdatedEventData;
import code.shubham.craft.shipment.dao.entities.Shipment;
import code.shubham.craft.shipment.dao.entities.ShipmentStatus;
import code.shubham.craft.shipment.dao.repositories.ShipmentRepository;
import code.shubham.craft.shipmentmodels.UpdateShipmentStatusRequest;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.StringUtils.truncate;

class ShipmentControllerTest extends AbstractSpringBootMVCTest {

	@Value("${shipment.kafka.topic.name}")
	private String topicName;

	private final String baseURL = "/v1/shipments";

	@Autowired
	private ShipmentRepository repository;

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
	protected void setUp() {
		super.setUp();
		truncate("shipments");
		this.kafkaConsumer.purge(this.topicName);
	}

	@AfterEach
	void tearDown() {
		truncate("shipments");
		this.kafkaConsumer.purge(this.topicName);
	}

	@Test
	void updateStatus_with_unauthorized_user() throws Exception {
		final UpdateShipmentStatusRequest request = UpdateShipmentStatusRequest.builder()
			.completedStatus(ShipmentStatus.PREPARE_TO_DISPATCH.name())
			.uniqueReferenceId(CraftTestConstants.SHIPMENT_UNIQUE_REFERENCE_ID)
			.build();
		UserIDContextHolder.set(TestCommonConstants.USER_ID);

		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/updateStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is4xxClientError())
			.andExpect(content().json("{\n" + "    \"statusCode\": 403,\n" + "    \"data\": null,\n"
					+ "    \"error\": \"Access denied\"\n" + "}"));
	}

	@Test
	void updateStatus_with_invalid_fields_in_request() throws Exception {
		final UpdateShipmentStatusRequest request = UpdateShipmentStatusRequest.builder()
			.completedStatus("")
			.uniqueReferenceId("")
			.build();
		UserIDContextHolder.set(TestCommonConstants.USER_ID);
		RoleContextHolder.set(Set.of("ADMIN"));
		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/updateStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is4xxClientError())
			.andExpect(content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n"
					+ "    \"error\": [\n" + "        {\n" + "            \"uniqueReferenceId\": [\n"
					+ "                \"uniqueReferenceId must not be empty.\"\n" + "            ],\n"
					+ "            \"completedStatus\": [\n"
					+ "                \"completedStatus must not be empty.\",\n"
					+ "                \"invalid value for completedStatus: \"\n" + "            ]\n" + "        }\n"
					+ "    ]\n" + "}"));

	}

	@Test
	void updateStatus_without_existing_shipment() throws Exception {
		final UpdateShipmentStatusRequest request = UpdateShipmentStatusRequest.builder()
			.completedStatus(ShipmentStatus.PREPARE_TO_DISPATCH.name())
			.uniqueReferenceId(CraftTestConstants.SHIPMENT_UNIQUE_REFERENCE_ID)
			.build();
		UserIDContextHolder.set(TestCommonConstants.USER_ID);
		RoleContextHolder.set(Set.of("ADMIN"));
		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/updateStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is4xxClientError())
			.andExpect(content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n"
					+ "    \"error\": [\n" + "        {\n" + "            \"uniqueReferenceId\": [\n"
					+ "                \"No shipment found for uniqueReferenceId: "
					+ CraftTestConstants.SHIPMENT_UNIQUE_REFERENCE_ID + "\"\n" + "            ]\n" + "        }\n"
					+ "    ]\n" + "}"));
	}

	@Test
	void updateStatus_without_shipment() throws Exception {
		final UpdateShipmentStatusRequest request = UpdateShipmentStatusRequest.builder()
			.completedStatus(ShipmentStatus.PREPARE_TO_DISPATCH.name())
			.uniqueReferenceId(CraftTestConstants.SHIPMENT_UNIQUE_REFERENCE_ID)
			.build();
		UserIDContextHolder.set(TestCommonConstants.USER_ID);
		RoleContextHolder.set(Set.of("ADMIN"));
		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/updateStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is4xxClientError())
			.andExpect(content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n"
					+ "    \"error\": [\n" + "        {\n" + "            \"uniqueReferenceId\": [\n"
					+ "                \"No shipment found for uniqueReferenceId: "
					+ CraftTestConstants.SHIPMENT_UNIQUE_REFERENCE_ID + "\"\n" + "            ]\n" + "        }\n"
					+ "    ]\n" + "}"));
	}

	@Test
	void updateStatus_invalid_status() throws Exception {
		final Shipment shipment = this.repository.save(Shipment.builder()
			.status(ShipmentStatus.PREPARE_TO_DISPATCH)
			.orderId(CraftTestConstants.ORDER_ID)
			.uniqueReferenceId(CraftTestConstants.SHIPMENT_UNIQUE_REFERENCE_ID)
			.build());

		final UpdateShipmentStatusRequest request = UpdateShipmentStatusRequest.builder()
			.completedStatus(ShipmentStatus.DISPATCHED.name())
			.uniqueReferenceId(CraftTestConstants.SHIPMENT_UNIQUE_REFERENCE_ID)
			.build();
		UserIDContextHolder.set(TestCommonConstants.USER_ID);
		RoleContextHolder.set(Set.of("ADMIN"));

		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/updateStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is4xxClientError())
			.andExpect(content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n"
					+ "    \"error\": [\n" + "        {\n" + "            \"completedStatus\": [\n"
					+ "                \"invalid status in request: " + request.getCompletedStatus() + "\"\n"
					+ "            ]\n" + "        }\n" + "    ]\n" + "}"));
	}

	@Test
	void updateStatus_PREPARE_TO_DISPATCH_Success() throws Exception {
		final Shipment shipment = this.repository.save(Shipment.builder()
			.status(ShipmentStatus.PREPARE_TO_DISPATCH)
			.orderId(CraftTestConstants.ORDER_ID)
			.uniqueReferenceId(CraftTestConstants.SHIPMENT_UNIQUE_REFERENCE_ID)
			.build());

		final UpdateShipmentStatusRequest request = UpdateShipmentStatusRequest.builder()
			.completedStatus(ShipmentStatus.PREPARE_TO_DISPATCH.name())
			.uniqueReferenceId(CraftTestConstants.SHIPMENT_UNIQUE_REFERENCE_ID)
			.build();
		UserIDContextHolder.set(TestCommonConstants.USER_ID);
		RoleContextHolder.set(Set.of("ADMIN"));

		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/updateStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().isOk());

		final var updated = this.repository.findByUniqueReferenceId(CraftTestConstants.SHIPMENT_UNIQUE_REFERENCE_ID);
		Assertions.assertTrue(updated.isPresent());
		Assertions.assertEquals(ShipmentStatus.DISPATCHED.name(), updated.get().getStatus().name());

		final Event event = this.kafkaConsumer.poll(1).get(0);
		Assertions.assertEquals(event.getUserId(), TestCommonConstants.USER_ID);
		Assertions.assertNotNull(event.getCreatedAt());
		Assertions.assertNotNull(event.getCorrelationId());
		Assertions.assertEquals(event.getEventName(), EventName.ShipmentStatusUpdated.name());
		Assertions.assertEquals(event.getEventType(), EventType.SHIPMENT.name());
		final Shipment data = JsonUtils.as(event.getData(), Shipment.class);
		Assertions.assertEquals(updated.get().getStatus().name(), data.getStatus().name());
	}

	@Test
	void updateStatus_DISPATCHED_Success() throws Exception {
		final Shipment shipment = this.repository.save(Shipment.builder()
			.status(ShipmentStatus.DISPATCHED)
			.orderId(CraftTestConstants.ORDER_ID)
			.uniqueReferenceId(CraftTestConstants.SHIPMENT_UNIQUE_REFERENCE_ID)
			.build());

		final UpdateShipmentStatusRequest request = UpdateShipmentStatusRequest.builder()
			.completedStatus(ShipmentStatus.DISPATCHED.name())
			.uniqueReferenceId(CraftTestConstants.SHIPMENT_UNIQUE_REFERENCE_ID)
			.build();
		UserIDContextHolder.set(TestCommonConstants.USER_ID);
		RoleContextHolder.set(Set.of("ADMIN"));

		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/updateStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().isOk());

		final var updated = this.repository.findByUniqueReferenceId(CraftTestConstants.SHIPMENT_UNIQUE_REFERENCE_ID);
		Assertions.assertTrue(updated.isPresent());
		Assertions.assertEquals(ShipmentStatus.ENROUTE.name(), updated.get().getStatus().name());

		final Event event = this.kafkaConsumer.poll(1).get(0);
		Assertions.assertEquals(event.getUserId(), TestCommonConstants.USER_ID);
		Assertions.assertNotNull(event.getCreatedAt());
		Assertions.assertNotNull(event.getCorrelationId());
		Assertions.assertEquals(event.getEventName(), EventName.ShipmentStatusUpdated.name());
		Assertions.assertEquals(event.getEventType(), EventType.SHIPMENT.name());
		final Shipment data = JsonUtils.as(event.getData(), Shipment.class);
		Assertions.assertEquals(data.getStatus().name(), updated.get().getStatus().name());
	}

	@Test
	void updateStatus_ENROUTE_Success() throws Exception {
		final Shipment shipment = this.repository.save(Shipment.builder()
			.status(ShipmentStatus.ENROUTE)
			.orderId(CraftTestConstants.ORDER_ID)
			.uniqueReferenceId(CraftTestConstants.SHIPMENT_UNIQUE_REFERENCE_ID)
			.build());

		final UpdateShipmentStatusRequest request = UpdateShipmentStatusRequest.builder()
			.completedStatus(ShipmentStatus.ENROUTE.name())
			.uniqueReferenceId(CraftTestConstants.SHIPMENT_UNIQUE_REFERENCE_ID)
			.build();
		UserIDContextHolder.set(TestCommonConstants.USER_ID);
		RoleContextHolder.set(Set.of("ADMIN"));

		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/updateStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().isOk());

		final var updated = this.repository.findByUniqueReferenceId(CraftTestConstants.SHIPMENT_UNIQUE_REFERENCE_ID);
		Assertions.assertTrue(updated.isPresent());
		Assertions.assertEquals(ShipmentStatus.DELIVERED.name(), updated.get().getStatus().name());

		final Event event = this.kafkaConsumer.poll(1).get(0);
		Assertions.assertEquals(event.getUserId(), TestCommonConstants.USER_ID);
		Assertions.assertNotNull(event.getCreatedAt());
		Assertions.assertNotNull(event.getCorrelationId());
		Assertions.assertEquals(event.getEventName(), EventName.ShipmentStatusUpdated.name());
		Assertions.assertEquals(event.getEventType(), EventType.SHIPMENT.name());
		final Shipment data = JsonUtils.as(event.getData(), Shipment.class);
		Assertions.assertEquals(updated.get().getStatus().name(), data.getStatus().name());
	}

	@Test
	void updateStatus_already_DELIVERED() throws Exception {
		final Shipment shipment = this.repository.save(Shipment.builder()
			.status(ShipmentStatus.DELIVERED)
			.orderId(CraftTestConstants.ORDER_ID)
			.uniqueReferenceId(CraftTestConstants.SHIPMENT_UNIQUE_REFERENCE_ID)
			.build());

		final UpdateShipmentStatusRequest request = UpdateShipmentStatusRequest.builder()
			.completedStatus(ShipmentStatus.ENROUTE.name())
			.uniqueReferenceId(CraftTestConstants.SHIPMENT_UNIQUE_REFERENCE_ID)
			.build();
		UserIDContextHolder.set(TestCommonConstants.USER_ID);
		RoleContextHolder.set(Set.of("ADMIN"));

		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/updateStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is4xxClientError())
			.andExpect(content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n"
					+ "    \"error\": [\n" + "        {\n" + "            \"uniqueReferenceId\": [\n"
					+ "                \"shipment delivered for uniqueReferenceId: "
					+ CraftTestConstants.SHIPMENT_UNIQUE_REFERENCE_ID + "\"\n" + "            ]\n" + "        }\n"
					+ "    ]\n" + "}"));
	}

}