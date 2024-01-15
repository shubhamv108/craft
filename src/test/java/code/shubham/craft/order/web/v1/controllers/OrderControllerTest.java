package code.shubham.craft.order.web.v1.controllers;

import code.shubham.commons.AbstractSpringBootMVCTest;
import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.core.iam.dao.entities.User;
import code.shubham.craft.CraftTestConstants;
import code.shubham.craft.order.dao.entities.Order;
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

class OrderControllerTest extends AbstractSpringBootMVCTest {

	private final String baseURL = "/v1/orders";

	@Autowired
	private OrderRepository repository;

	@BeforeEach
	public void setUp() {
		super.setUp();
		truncate("orders");
	}

	@AfterEach
	void tearDown() {
		truncate("orders");
	}

	@Disabled
	@Test
	void getAllOrders_Success() throws Exception {
		final Order created = this.repository.save(Order.builder()
			.uniqueReferenceId(CraftTestConstants.ORDER_UNIQUE_REFERENCE_ID)
			.userId(TestCommonConstants.USER_ID)
			.build());
		UserIDContextHolder.set(TestCommonConstants.USER_ID);

		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.baseURL)
				.contentType(MediaType.APPLICATION_JSON)
				.param("userId", TestCommonConstants.USER_ID)
				.content(as(null)))
			.andExpect(status().is2xxSuccessful())
			.andExpect(
					content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n" + "    \"error\": [\n"
							+ "        {\n" + "            \"userId\": [\n" + "                \"User with userId: "
							+ TestCommonConstants.USER_ID + " not allowed to perform the operation\"\n"
							+ "            ]\n" + "        }\n" + "    ]\n" + "}"));

	}

}