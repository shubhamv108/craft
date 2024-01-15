package code.shubham.craft.driver.cab.web.v1.controllers;

import code.shubham.commons.AbstractSpringBootMVCTest;
import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.craft.CraftTestConstants;
import code.shubham.craft.driver.cab.dao.entities.Cab;
import code.shubham.craft.driver.cab.dao.repositories.CabRepository;
import code.shubham.craft.driver.dao.entities.Driver;
import code.shubham.craft.driver.dao.entities.DriverStatus;
import code.shubham.craft.driver.dao.repositories.DriverRepository;
import code.shubham.craft.order.dao.entities.Order;
import code.shubham.craft.order.dao.entities.OrderProduct;
import code.shubham.craft.order.dao.entities.OrderProductStatus;
import code.shubham.craft.order.dao.entities.OrderStatus;
import code.shubham.craft.order.dao.repositories.OrderProductRepository;
import code.shubham.craft.order.dao.repositories.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CabControllerTest extends AbstractSpringBootMVCTest {

	private final String baseURL = "/v1/drivers";

	@Autowired
	private CabRepository repository;

	@Autowired
	private DriverRepository driverRepository;

	@BeforeEach
	public void setUp() {
		super.setUp();
		truncate("drivers");
		truncate("cabs");
		UserIDContextHolder.set(TestCommonConstants.USER_ID);
	}

	@AfterEach
	void tearDown() {
		truncate("drivers");
		truncate("cabs");
	}

	@Test
	void getAll_with_invalid_userId() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.baseURL + "/" + CraftTestConstants.DRIVER_ID + "/cabs")
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
	void getAll_with_no_existing_driver() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.baseURL + "/" + CraftTestConstants.DRIVER_ID + "/cabs")
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
	void getAll_Success() throws Exception {
		final Driver existingDriver = this.driverRepository.save(Driver.builder()
			.userId(TestCommonConstants.USER_ID)
			.drivingLicenseName(CraftTestConstants.DRIVING_LICENSE_NAME)
			.drivingLicense(CraftTestConstants.DRIVING_LICENSE_NUMBER)
			.status(DriverStatus.ACTIVE)
			.build());

		final Cab existingCab = this.repository.save(Cab.builder()
			.driverId(existingDriver.getId())
			.registrationNumber(CraftTestConstants.VEHICLE_REGISTRATION_NUMBER)
			.color(CraftTestConstants.VEHICLE_COLOR)
			.userId(TestCommonConstants.USER_ID)
			.build());

		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.baseURL + "/" + existingDriver.getId() + "/cabs")
				.contentType(MediaType.APPLICATION_JSON)
				.param("userId", TestCommonConstants.USER_ID)
				.content(as(null)))
			.andExpect(status().is(302))
			.andExpect(content().json("{\n" + "    \"statusCode\": 302,\n" + "    \"data\": [\n" + "        {\n"
					+ "            \"registrationNumber\": \"" + existingCab.getRegistrationNumber() + "\",\n"
					+ "            \"color\": \"" + existingCab.getColor() + "\",\n" + "            \"driverId\": \""
					+ existingDriver.getId() + "\",\n" + "            \"userId\": \"" + TestCommonConstants.USER_ID
					+ "\"\n" + "        }\n" + "    ],\n" + "    \"error\": null\n" + "}"));
	}

}