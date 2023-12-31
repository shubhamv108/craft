package code.shubham.craft.shipment.web.v1.controllers;

import code.shubham.commons.AbstractMVCTest;
import code.shubham.commons.CommonTestConstants;
import code.shubham.commons.contexts.RoleContextHolder;
import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.craft.CraftTestConstants;
import code.shubham.craft.shipment.dao.entities.Shipment;
import code.shubham.craft.shipment.dao.entities.ShipmentStatus;
import code.shubham.craft.shipment.dao.repositories.ShipmentRepository;
import code.shubham.craft.shipmentmodels.UpdateShipmentStatusRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.StringUtils.truncate;

class ShipmentControllerTest extends AbstractMVCTest {

	private final String baseURL = "/v1/shipments";

	@Autowired
	private ShipmentRepository repository;

	@BeforeEach
	protected void setUp() {
		super.setUp();
		truncate("shipments");
	}

	@AfterEach
	void tearDown() {
		truncate("shipments");
	}

	@Test
	void updateStatus_with_unauthorized_user() throws Exception {
		final UpdateShipmentStatusRequest request = UpdateShipmentStatusRequest.builder()
			.completedStatus(ShipmentStatus.PREPARE_TO_DISPATCH.name())
			.uniqueReferenceId(CraftTestConstants.SHIPMENT_UNIQUE_REFERENCE_ID)
			.build();
		UserIDContextHolder.set(CommonTestConstants.USER_ID);

		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/updateStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is4xxClientError())
			.andExpect(content().json("{\n" + "    \"statusCode\": 403,\n" + "    \"data\": null,\n"
					+ "    \"error\": \"Access denied\"\n" + "}"));
	}

	@Test
	void updateStatus_without_existing_shipment() throws Exception {
		final UpdateShipmentStatusRequest request = UpdateShipmentStatusRequest.builder()
			.completedStatus(ShipmentStatus.PREPARE_TO_DISPATCH.name())
			.uniqueReferenceId(CraftTestConstants.SHIPMENT_UNIQUE_REFERENCE_ID)
			.build();
		UserIDContextHolder.set(CommonTestConstants.USER_ID);
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
		UserIDContextHolder.set(CommonTestConstants.USER_ID);
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
		UserIDContextHolder.set(CommonTestConstants.USER_ID);
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
		UserIDContextHolder.set(CommonTestConstants.USER_ID);
		RoleContextHolder.set(Set.of("ADMIN"));

		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/updateStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().isOk());
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
		UserIDContextHolder.set(CommonTestConstants.USER_ID);
		RoleContextHolder.set(Set.of("ADMIN"));

		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/updateStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().isOk());
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
		UserIDContextHolder.set(CommonTestConstants.USER_ID);
		RoleContextHolder.set(Set.of("ADMIN"));

		this.mockMvc
			.perform(MockMvcRequestBuilders.patch(this.baseURL + "/updateStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().isOk());
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
		UserIDContextHolder.set(CommonTestConstants.USER_ID);
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