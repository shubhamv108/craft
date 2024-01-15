package code.shubham.craft.order.web.v1.controllers;

import code.shubham.commons.AbstractSpringBootMVCTest;
import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.craft.CraftTestConstants;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderProductControllerTest extends AbstractSpringBootMVCTest {

	private final String baseURL = "/v1/orders";

	@Autowired
	private OrderRepository repository;

	@Autowired
	private OrderProductRepository orderProductRepository;

	@BeforeEach
	public void setUp() {
		super.setUp();
		truncate("orders");
		truncate("order__products_mapping");
		UserIDContextHolder.set(TestCommonConstants.USER_ID);
	}

	@AfterEach
	void tearDown() {
		truncate("orders");
		truncate("order__products_mapping");
	}

	@Test
	void getAllOrderProducts_with_invalid_userId() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.baseURL + "/" + CraftTestConstants.ORDER_ID + "/products")
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
	void getAllOrderProducts_with_no_existing_product() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.baseURL + "/" + CraftTestConstants.ORDER_ID + "/products")
				.contentType(MediaType.APPLICATION_JSON)
				.param("userId", TestCommonConstants.USER_ID)
				.content(as(null)))
			.andExpect(status().is(400))
			.andExpect(content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n"
					+ "    \"error\": [\n" + "        {\n" + "            \"orderId\": [\n"
					+ "                \"No order found for oderId: " + CraftTestConstants.ORDER_ID + "\"\n"
					+ "            ]\n" + "        }\n" + "    ]\n" + "}"));
	}

	@Test
	void getAllOrderProducts_Success() throws Exception {
		final Order existingOrder = this.repository.save(Order.builder()
			.customerId(TestCommonConstants.USER_ID)
			.customerType("DRIVER")
			.uniqueReferenceId(CraftTestConstants.ORDER_UNIQUE_REFERENCE_ID)
			.status(OrderStatus.CREATED)
			.userId(TestCommonConstants.USER_ID)
			.build());

		final OrderProduct existingProduct = this.orderProductRepository.save(OrderProduct.builder()
			.orderId(existingOrder.getId())
			.status(OrderProductStatus.SHIPPED)
			.quantity(1)
			.uniqueReferenceId(CraftTestConstants.ORDER_PRODUCT_UNIQUE_REFERENCE_ID)
			.productId(CraftTestConstants.PRODUCT_ID)
			.build());

		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.baseURL + "/" + existingOrder.getId() + "/products")
				.contentType(MediaType.APPLICATION_JSON)
				.param("userId", TestCommonConstants.USER_ID)
				.content(as(null)))
			.andExpect(status().is(302))
			.andExpect(content().json("{\n" + "    \"statusCode\": 302,\n" + "    \"data\": [\n" + "        {\n"
					+ "            \"orderId\": \"" + existingOrder.getId() + "\",\n" + "            \"productId\": \""
					+ CraftTestConstants.PRODUCT_ID + "\",\n" + "            \"quantity\": 1,\n"
					+ "            \"status\": \"SHIPPED\",\n" + "            \"uniqueReferenceId\": \""
					+ existingProduct.getUniqueReferenceId() + "\"\n" + "        }\n" + "    ],\n"
					+ "    \"error\": null\n" + "}"));
	}

}