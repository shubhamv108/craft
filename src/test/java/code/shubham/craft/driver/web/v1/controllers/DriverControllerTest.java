package code.shubham.craft.driver.web.v1.controllers;

import code.shubham.commons.AbstractSpringBootMVCTest;
import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.TestKafkaConsumer;
import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.commons.models.Event;
import code.shubham.commons.utils.JsonUtils;
import code.shubham.commons.utils.UUIDUtils;
import code.shubham.craft.CraftTestConstants;
import code.shubham.craft.constants.EventName;
import code.shubham.craft.constants.EventType;
import code.shubham.craft.driver.cab.dao.entities.Cab;
import code.shubham.craft.driver.cab.dao.repositories.CabRepository;
import code.shubham.craft.driver.cabmodels.CabDTO;
import code.shubham.craft.driver.dao.entities.Driver;
import code.shubham.craft.driver.dao.entities.DriverStatus;
import code.shubham.craft.driver.dao.repositories.DriverRepository;
import code.shubham.craft.drivermodels.MarkAvailableForRideRequest;
import code.shubham.craft.drivermodels.RegisterDriver;
import code.shubham.craft.driveronboard.TestCraftUtils;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DriverControllerTest extends AbstractSpringBootMVCTest {

	@Value("${driver.kafka.topic.name}")
	private String topicName;

	private final String baseURL = "/v1/drivers";

	@Autowired
	private DriverRepository repository;

	@Autowired
	private CabRepository cabRepository;

	private TestKafkaConsumer kafkaConsumer;

	private String registrationNumber;

	private String color;

	private String drivingLicenseName;

	private String drivingLicenseNumber;

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
		truncate("drivers");
		truncate("cabs");
		this.kafkaConsumer.purge(this.topicName);
		this.registrationNumber = "ka01ab1234";
		this.color = "white";
		this.drivingLicenseName = "Shubham";
		this.drivingLicenseNumber = "ka0174923874";
		TestCraftUtils.setContext();
	}

	@AfterEach
	void tearDown() {
		truncate("drivers");
		truncate("cabs");
		this.kafkaConsumer.purge(this.topicName);
	}

	@Test
	void register_without_request_body() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post(this.baseURL + "/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(null)))
			.andExpect(status().is(400))
			.andExpect(content().json(
					"{\"type\":\"about:blank\",\"title\":\"Bad Request\",\"status\":400,\"detail\":\"Failed to read request\",\"instance\":\"/v1/drivers/register\"}"));

		final var created = this.repository.findByUserId(TestCommonConstants.USER_ID);
		final var createdCab = this.cabRepository.findByRegistrationNumber(this.registrationNumber);
		Assertions.assertFalse(created.isPresent());
		Assertions.assertFalse(createdCab.isPresent());
	}

	@Test
	void register_with_invalid_param_request() throws Exception {
		final RegisterDriver.Request request = RegisterDriver.Request.builder()
			.drivingLicense("")
			.drivingLicenseName("")
			.cab(CabDTO.builder().registrationNumber("").color("").build())
			.build();
		this.mockMvc
			.perform(MockMvcRequestBuilders.post(this.baseURL + "/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is(400))
			.andExpect(content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n"
					+ "    \"error\": [\n" + "        {\n" + "            \"drivingLicense\": [\n"
					+ "                \"drivingLicense must not be empty.\"\n" + "            ],\n"
					+ "            \"drivingLicenseName\": [\n"
					+ "                \"drivingLicenseName must not be empty.\"\n" + "            ],\n"
					+ "            \"cab.registrationNumber\": [\n"
					+ "                \"cab.registrationNumber must not be empty.\"\n" + "            ],\n"
					+ "            \"cab.color\": [\n" + "                \"cab.color must not be empty.\"\n"
					+ "            ]\n" + "        }\n" + "    ]\n" + "}"));

		final var created = this.repository.findByUserId(TestCommonConstants.USER_ID);
		final var createdCab = this.cabRepository.findByRegistrationNumber(this.registrationNumber);
		Assertions.assertFalse(created.isPresent());
		Assertions.assertFalse(createdCab.isPresent());
	}

	@Test
	void register_for_already_registered_driver() throws Exception {
		this.repository.save(Driver.builder()
			.drivingLicenseName(this.drivingLicenseName)
			.status(DriverStatus.ACTIVE)
			.drivingLicense(this.drivingLicenseNumber)
			.userId(TestCommonConstants.USER_ID)
			.build());

		final RegisterDriver.Request request = RegisterDriver.Request.builder()
			.drivingLicense(this.drivingLicenseNumber)
			.drivingLicenseName(drivingLicenseName)
			.cab(CabDTO.builder().registrationNumber(registrationNumber).color(color).build())
			.build();

		this.mockMvc
			.perform(MockMvcRequestBuilders.post(this.baseURL + "/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is(400))
			.andExpect(content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n"
					+ "    \"error\": [\n" + "        {\n" + "            \"email\": [\n"
					+ "                \"User with email: " + TestCommonConstants.USER_EMAIL
					+ " already registered as a driver\"\n" + "            ]\n" + "        }\n" + "    ]\n" + "}"));

		final var createdCab = this.cabRepository.findByRegistrationNumber(this.registrationNumber);
		Assertions.assertFalse(createdCab.isPresent());
	}

	@Test
	void register_with_existing_cab() throws Exception {
		final Cab existingCab = this.cabRepository.save(Cab.builder()
			.driverId("OTHER_DRIVER_ID")
			.registrationNumber(this.registrationNumber)
			.userId(TestCommonConstants.USER_ID)
			.color(this.color)
			.build());
		final RegisterDriver.Request request = RegisterDriver.Request.builder()
			.drivingLicense(this.drivingLicenseNumber)
			.drivingLicenseName(drivingLicenseName)
			.cab(CabDTO.builder().registrationNumber(registrationNumber).color(color).build())
			.build();

		this.mockMvc
			.perform(MockMvcRequestBuilders.post(this.baseURL + "/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is(400))
			.andExpect(content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n"
					+ "    \"error\": [\n" + "        {\n" + "            \"vehicleRegistrationNumber\": [\n"
					+ "                \"Cab with registration number " + this.registrationNumber
					+ " already exists\"\n" + "            ]\n" + "        }\n" + "    ]\n" + "}"));

		final var created = this.repository.findByUserId(TestCommonConstants.USER_ID);
		final var updatedCab = this.cabRepository.findById(existingCab.getId());

		Assertions.assertFalse(created.isPresent());
		Assertions.assertEquals(existingCab.getDriverId(), updatedCab.get().getDriverId());
	}

	@Test
	void register_Success() throws Exception {
		final RegisterDriver.Request request = RegisterDriver.Request.builder()
			.drivingLicense(this.drivingLicenseNumber)
			.drivingLicenseName(drivingLicenseName)
			.cab(CabDTO.builder().registrationNumber(registrationNumber).color(color).build())
			.build();

		this.mockMvc
			.perform(MockMvcRequestBuilders.post(this.baseURL + "/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is(201))
			.andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(201))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.drivingLicenseName").value(this.drivingLicenseName))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.drivingLicense").value(this.drivingLicenseNumber))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.userId").value(TestCommonConstants.USER_ID))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.cab.registrationNumber").value(this.registrationNumber))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.cab.color").value(this.color));

		final var created = this.repository.findByUserId(TestCommonConstants.USER_ID);
		final var createdCab = this.cabRepository.findByRegistrationNumber(this.registrationNumber);
		final Event event = this.kafkaConsumer.poll(1).get(0);
		final Driver data = JsonUtils.as(event.getData(), Driver.class);

		Assertions.assertTrue(created.isPresent());
		Assertions.assertEquals(DriverStatus.ONBOARDING.name(), created.get().getStatus().name());
		Assertions.assertEquals(this.drivingLicenseNumber, created.get().getDrivingLicense());
		Assertions.assertEquals(this.drivingLicenseName, created.get().getDrivingLicenseName());
		Assertions.assertEquals(TestCommonConstants.USER_ID, created.get().getUserId());

		Assertions.assertTrue(createdCab.isPresent());
		Assertions.assertEquals(this.color, createdCab.get().getColor());
		Assertions.assertEquals(TestCommonConstants.USER_ID, createdCab.get().getUserId());

		Assertions.assertEquals(event.getUserId(), TestCommonConstants.USER_ID);
		Assertions.assertNotNull(event.getCreatedAt());
		Assertions.assertNotNull(event.getCorrelationId());
		Assertions.assertEquals(event.getUniqueReferenceId(),
				UUIDUtils.uuid5(created.get().getId() + "_" + created.get().getUpdatedAt().getTime()));
		Assertions.assertEquals(event.getEventName(), EventName.DriverStatusUpdated.name());
		Assertions.assertEquals(event.getEventType(), EventType.DRIVER.name());
		Assertions.assertEquals(data.getStatus().name(), created.get().getStatus().name());
	}

	@Test
	void markAvailableForRide_with_invalid_userid_in_request() throws Exception {
		final Driver existingDriver = this.repository.save(Driver.builder()
			.drivingLicenseName(this.drivingLicenseName)
			.drivingLicense(this.drivingLicenseNumber)
			.status(DriverStatus.ACTIVE)
			.userId(TestCommonConstants.USER_ID)
			.build());
		final MarkAvailableForRideRequest request = MarkAvailableForRideRequest.builder()
			.driverId(CraftTestConstants.DRIVER_ID)
			.userId("INVALID_USER_ID")
			.vehicleRegistrationNumber(this.registrationNumber)
			.build();

		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/availableForRide")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is(400))
			.andExpect(content().json(
					"{\"statusCode\":400,\"data\":null,\"error\":[{\"userId\":[\"User with userId: INVALID_USER_ID not allowed to perform the operation\"]}]}"));

		final var updated = this.repository.findById(existingDriver.getId());
		Assertions.assertTrue(updated.isPresent());
		Assertions.assertEquals(DriverStatus.ACTIVE.name(), updated.get().getStatus().name());
		Assertions.assertEquals(this.drivingLicenseNumber, updated.get().getDrivingLicense());
		Assertions.assertEquals(this.drivingLicenseName, updated.get().getDrivingLicenseName());
		Assertions.assertEquals(TestCommonConstants.USER_ID, updated.get().getUserId());
		Assertions.assertNull(updated.get().getActiveCabId());
	}

	@Test
	void markAvailableForRide_with_invalid_fields_in_request() throws Exception {
		final Driver existingDriver = this.repository.save(Driver.builder()
			.drivingLicenseName(this.drivingLicenseName)
			.drivingLicense(this.drivingLicenseNumber)
			.status(DriverStatus.ACTIVE)
			.userId(TestCommonConstants.USER_ID)
			.build());
		final MarkAvailableForRideRequest request = MarkAvailableForRideRequest.builder()
			.driverId("")
			.userId(TestCommonConstants.USER_ID)
			.vehicleRegistrationNumber("")
			.build();

		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/availableForRide")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is(400))
			.andExpect(content().json(
					"{\"statusCode\":400,\"data\":null,\"error\":[{\"driverId\":[\"driverId must not be empty.\"],\"vehicleRegistrationNumber\":[\"vehicleRegistrationNumber must not be empty.\"]}]}"));

		final var updated = this.repository.findById(existingDriver.getId());

		Assertions.assertTrue(updated.isPresent());
		Assertions.assertEquals(DriverStatus.ACTIVE.name(), updated.get().getStatus().name());
		Assertions.assertEquals(this.drivingLicenseNumber, updated.get().getDrivingLicense());
		Assertions.assertEquals(this.drivingLicenseName, updated.get().getDrivingLicenseName());
		Assertions.assertEquals(TestCommonConstants.USER_ID, updated.get().getUserId());
		Assertions.assertNull(updated.get().getActiveCabId());
	}

	@Test
	void markAvailableForRide_with_inactive_driver() throws Exception {
		final Driver existingDriver = this.repository.save(Driver.builder()
			.drivingLicenseName(this.drivingLicenseName)
			.drivingLicense(this.drivingLicenseNumber)
			.status(DriverStatus.ONBOARDING)
			.userId(TestCommonConstants.USER_ID)
			.build());
		final MarkAvailableForRideRequest request = MarkAvailableForRideRequest.builder()
			.driverId(existingDriver.getId())
			.userId(TestCommonConstants.USER_ID)
			.vehicleRegistrationNumber(this.registrationNumber)
			.build();

		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/availableForRide")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is(400))
			.andExpect(content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n"
					+ "    \"error\": [\n" + "        {\n" + "            \"driverId\": [\n"
					+ "                \"driver with id: " + existingDriver.getId() + " not active\"\n"
					+ "            ]\n" + "        }\n" + "    ]\n" + "}"));

		final var updated = this.repository.findById(existingDriver.getId());

		Assertions.assertTrue(updated.isPresent());
		Assertions.assertEquals(DriverStatus.ONBOARDING.name(), updated.get().getStatus().name());
		Assertions.assertEquals(this.drivingLicenseNumber, updated.get().getDrivingLicense());
		Assertions.assertEquals(this.drivingLicenseName, updated.get().getDrivingLicenseName());
		Assertions.assertEquals(TestCommonConstants.USER_ID, updated.get().getUserId());
		Assertions.assertNull(updated.get().getActiveCabId());
	}

	@Test
	void markAvailableForRide_with_no_such_cab_registered_with_driver() throws Exception {
		final Driver existingDriver = this.repository.save(Driver.builder()
			.drivingLicenseName(this.drivingLicenseName)
			.drivingLicense(this.drivingLicenseNumber)
			.status(DriverStatus.ACTIVE)
			.userId(TestCommonConstants.USER_ID)
			.build());
		final MarkAvailableForRideRequest request = MarkAvailableForRideRequest.builder()
			.driverId(existingDriver.getId())
			.userId(TestCommonConstants.USER_ID)
			.vehicleRegistrationNumber("ka01cd1234")
			.build();

		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/availableForRide")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is(400))
			.andExpect(content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n"
					+ "    \"error\": [\n" + "        {\n" + "            \"vehicleRegistrationNumber\": [\n"
					+ "                \"No cab found for vehicleRegistrationNumber: ka01cd1234\"\n" + "            ]\n"
					+ "        }\n" + "    ]\n" + "}"));

		final var updated = this.repository.findById(existingDriver.getId());

		Assertions.assertTrue(updated.isPresent());
		Assertions.assertEquals(DriverStatus.ACTIVE.name(), updated.get().getStatus().name());
		Assertions.assertEquals(this.drivingLicenseNumber, updated.get().getDrivingLicense());
		Assertions.assertEquals(this.drivingLicenseName, updated.get().getDrivingLicenseName());
		Assertions.assertEquals(TestCommonConstants.USER_ID, updated.get().getUserId());
		Assertions.assertNull(updated.get().getActiveCabId());
	}

	@Test
	void markAvailableForRide_Success() throws Exception {
		final Driver existingDriver = this.repository.save(Driver.builder()
			.drivingLicenseName(this.drivingLicenseName)
			.drivingLicense(this.drivingLicenseNumber)
			.status(DriverStatus.ACTIVE)
			.userId(TestCommonConstants.USER_ID)
			.build());
		final Cab existingCab = this.cabRepository.save(Cab.builder()
			.driverId(existingDriver.getId())
			.registrationNumber(this.registrationNumber)
			.userId(TestCommonConstants.USER_ID)
			.color(this.color)
			.build());
		final MarkAvailableForRideRequest request = MarkAvailableForRideRequest.builder()
			.driverId(existingDriver.getId())
			.userId(TestCommonConstants.USER_ID)
			.vehicleRegistrationNumber(this.registrationNumber)
			.build();

		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/availableForRide")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is(200))
			.andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(200))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.drivingLicenseName").value(this.drivingLicenseName))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.drivingLicense").value(this.drivingLicenseNumber))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.userId").value(TestCommonConstants.USER_ID))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.status").value(DriverStatus.ACTIVE.name()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.activeCabId").value(existingCab.getId()));

		final var updated = this.repository.findById(existingDriver.getId());

		Assertions.assertTrue(updated.isPresent());
		Assertions.assertEquals(DriverStatus.ACTIVE.name(), updated.get().getStatus().name());
		Assertions.assertEquals(this.drivingLicenseNumber, updated.get().getDrivingLicense());
		Assertions.assertEquals(this.drivingLicenseName, updated.get().getDrivingLicenseName());
		Assertions.assertEquals(TestCommonConstants.USER_ID, updated.get().getUserId());
		Assertions.assertEquals(existingCab.getId(), updated.get().getActiveCabId());
	}

	@Test
	void getById_without_userId_param() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.baseURL + "/" + CraftTestConstants.DRIVER_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(null)))
			.andExpect(status().is(400))
			.andExpect(content().json(
					"{\"type\":\"about:blank\",\"title\":\"Bad Request\",\"status\":400,\"detail\":\"Required parameter 'userId' is not present.\",\"instance\":\"/v1/drivers/b2dca92b-fe74-47f6-a369-8940f6291d72\"}"));
	}

	@Test
	void getById_with_invalid_user() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.baseURL + "/" + CraftTestConstants.DRIVER_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.param("userId", "INVALID_USER_ID")
				.content(as(null)))
			.andExpect(status().is(400))
			.andExpect(
					content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n" + "    \"error\": [\n"
							+ "        {\n" + "            \"userId\": [\n" + "                \"User with userId: "
							+ "INVALID_USER_ID" + " not allowed to perform the operation\"\n" + "            ]\n"
							+ "        }\n" + "    ]\n" + "}"));
	}

	@Test
	void getById_without_existing_driver() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.baseURL + "/" + CraftTestConstants.DRIVER_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.param("userId", TestCommonConstants.USER_ID)
				.content(as(null)))
			.andExpect(status().is(400))
			.andExpect(content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n"
					+ "    \"error\": [\n" + "        {\n" + "            \"driverId\": [\n"
					+ "                \"no driver found for driverId: " + CraftTestConstants.DRIVER_ID + "\"\n"
					+ "            ]\n" + "        }\n" + "    ]\n" + "}"));
	}

	@Test
	void getById_Success() throws Exception {
		final Driver existingDriver = this.repository.save(Driver.builder()
			.drivingLicenseName(this.drivingLicenseName)
			.drivingLicense(this.drivingLicenseNumber)
			.status(DriverStatus.ACTIVE)
			.userId(TestCommonConstants.USER_ID)
			.build());

		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.baseURL + "/" + existingDriver.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.param("userId", TestCommonConstants.USER_ID)
				.content(as(null)))
			.andExpect(status().is(302))
			.andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(302))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.driverId").value(existingDriver.getId()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.drivingLicenseName").value(this.drivingLicenseName))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.drivingLicense").value(this.drivingLicenseNumber))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.userId").value(TestCommonConstants.USER_ID))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.status").value(DriverStatus.ACTIVE.name()));
	}

	@Test
	void getById_with_cabs_Success() throws Exception {
		Driver existingDriver = this.repository.save(Driver.builder()
			.drivingLicenseName(this.drivingLicenseName)
			.drivingLicense(this.drivingLicenseNumber)
			.status(DriverStatus.ACTIVE)
			.userId(TestCommonConstants.USER_ID)
			.build());

		final Cab existingCab = this.cabRepository.save(Cab.builder()
			.userId(TestCommonConstants.USER_ID)
			.driverId(existingDriver.getId())
			.color(this.color)
			.registrationNumber(this.registrationNumber)
			.build());

		existingDriver.setActiveCabId(existingCab.getId());
		existingDriver = this.repository.save(existingDriver);

		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.baseURL + "/" + existingDriver.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.param("userId", TestCommonConstants.USER_ID)
				.content(as(null)))
			.andExpect(status().is(302))
			.andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(302))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.driverId").value(existingDriver.getId()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.drivingLicenseName").value(this.drivingLicenseName))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.drivingLicense").value(this.drivingLicenseNumber))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.userId").value(TestCommonConstants.USER_ID))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.status").value(DriverStatus.ACTIVE.name()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.activeCabId").value(existingDriver.getActiveCabId()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.cabs[0].cabId").value(existingCab.getId()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.cabs[0].registrationNumber")
				.value(existingCab.getRegistrationNumber()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.cabs[0].color").value(existingCab.getColor()));
	}

}