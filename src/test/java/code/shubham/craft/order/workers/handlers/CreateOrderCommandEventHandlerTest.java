package code.shubham.craft.order.workers.handlers;

import code.shubham.commons.AbstractSpringBootTest;
import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.models.Event;
import code.shubham.commons.utils.UUIDUtils;
import code.shubham.craft.CraftTestConstants;
import code.shubham.craft.TestEventUtils;
import code.shubham.craft.order.dao.entities.OrderProductStatus;
import code.shubham.craft.order.dao.entities.OrderStatus;
import code.shubham.craft.order.dao.repositories.OrderProductRepository;
import code.shubham.craft.order.dao.repositories.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateOrderCommandEventHandlerTest extends AbstractSpringBootTest {

	@Autowired
	private CreateOrderCommandEventHandler handler;

	@Autowired
	private OrderRepository repository;

	@Autowired
	private OrderProductRepository orderProductRepository;

	@BeforeEach
	public void setUp() {
		super.setUp();
		truncate("orders");
		truncate("order__products_mapping");
	}

	@AfterEach
	void tearDown() {
		truncate("orders");
		truncate("order__products_mapping");
	}

	@Test
	void handle_success() {
		final Event event = TestEventUtils.getCreateOrderCommandEvent();

		this.handler.handle(event);

		final var orders = this.repository.findAllByUserId(TestCommonConstants.USER_ID);
		final var orderProducts = this.orderProductRepository.findAllByOrderId(orders.get(0).getId());
		assertEquals(1, orders.size());
		assertEquals(1, orderProducts.size());
		assertEquals(TestCommonConstants.USER_ID, orders.get(0).getUserId());
		assertEquals(OrderStatus.CREATED, orders.get(0).getStatus());
		assertEquals(CraftTestConstants.ORDER_UNIQUE_REFERENCE_ID, orders.get(0).getUniqueReferenceId());
		assertEquals(CraftTestConstants.DRIVER_ID, orders.get(0).getCustomerId());
		assertEquals("DRIVER", orders.get(0).getCustomerType());
		assertEquals(orders.get(0).getId(), orderProducts.get(0).getOrderId());
		assertEquals(CraftTestConstants.PRODUCT_ID, orderProducts.get(0).getProductId());
		assertEquals(OrderProductStatus.CREATED.name(), orderProducts.get(0).getStatus().name());
		assertEquals(1, orderProducts.get(0).getQuantity());
		assertEquals(
				UUIDUtils.uuid5(CraftTestConstants.ORDER_UNIQUE_REFERENCE_ID + "_" + CraftTestConstants.PRODUCT_ID),
				orderProducts.get(0).getUniqueReferenceId());
	}

}