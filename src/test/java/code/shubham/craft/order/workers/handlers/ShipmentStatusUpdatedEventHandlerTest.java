package code.shubham.craft.order.workers.handlers;

import code.shubham.commons.AbstractSpringBootTest;
import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.models.Event;
import code.shubham.commons.utils.UUIDUtils;
import code.shubham.craft.CraftTestConstants;
import code.shubham.craft.TestEventUtils;
import code.shubham.craft.order.dao.entities.Order;
import code.shubham.craft.order.dao.entities.OrderProduct;
import code.shubham.craft.order.dao.entities.OrderProductStatus;
import code.shubham.craft.order.dao.entities.OrderStatus;
import code.shubham.craft.order.dao.repositories.OrderProductRepository;
import code.shubham.craft.order.dao.repositories.OrderRepository;
import code.shubham.craft.shipment.dao.entities.ShipmentStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.util.StringUtils.truncate;

class ShipmentStatusUpdatedEventHandlerTest extends AbstractSpringBootTest {

	@Autowired
	private ShipmentStatusUpdatedEventHandler handler;

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
	void handle_PREPARE_TO_DISPATCH_shipment_status() {
		final Order exisitingOrder = this.repository.save(Order.builder()
			.status(OrderStatus.CREATED)
			.uniqueReferenceId(CraftTestConstants.ORDER_UNIQUE_REFERENCE_ID)
			.userId(TestCommonConstants.USER_ID)
			.customerId(CraftTestConstants.DRIVER_ID)
			.customerType("DRIVER")
			.build());

		final OrderProduct exisitingOrderProduct = this.orderProductRepository.save(OrderProduct.builder()
			.status(OrderProductStatus.CREATED)
			.uniqueReferenceId(
					UUIDUtils.uuid5(CraftTestConstants.ORDER_UNIQUE_REFERENCE_ID + "_" + CraftTestConstants.PRODUCT_ID))
			.orderId(exisitingOrder.getId())
			.productId(CraftTestConstants.PRODUCT_ID)
			.quantity(1)
			.build());

		final Event event = TestEventUtils.getShipmentStatusUpdatedEvent(ShipmentStatus.PREPARE_TO_DISPATCH,
				exisitingOrder.getId());

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
		assertEquals(OrderProductStatus.SHIPPED.name(), orderProducts.get(0).getStatus().name());
		assertEquals(1, orderProducts.get(0).getQuantity());
		assertEquals(
				UUIDUtils.uuid5(CraftTestConstants.ORDER_UNIQUE_REFERENCE_ID + "_" + CraftTestConstants.PRODUCT_ID),
				orderProducts.get(0).getUniqueReferenceId());
	}

	@Test
	void handle_DELIVERED_shipment_status() {
		final Order exisitingOrder = this.repository.save(Order.builder()
			.status(OrderStatus.CREATED)
			.uniqueReferenceId(CraftTestConstants.ORDER_UNIQUE_REFERENCE_ID)
			.userId(TestCommonConstants.USER_ID)
			.customerId(CraftTestConstants.DRIVER_ID)
			.customerType("DRIVER")
			.build());

		final OrderProduct exisitingOrderProduct = this.orderProductRepository.save(OrderProduct.builder()
			.status(OrderProductStatus.SHIPPED)
			.uniqueReferenceId(
					UUIDUtils.uuid5(CraftTestConstants.ORDER_UNIQUE_REFERENCE_ID + "_" + CraftTestConstants.PRODUCT_ID))
			.orderId(exisitingOrder.getId())
			.productId(CraftTestConstants.PRODUCT_ID)
			.quantity(1)
			.build());

		final Event event = TestEventUtils.getShipmentStatusUpdatedEvent(ShipmentStatus.DELIVERED,
				exisitingOrder.getId());

		this.handler.handle(event);

		final var orders = this.repository.findAllByUserId(TestCommonConstants.USER_ID);
		final var orderProducts = this.orderProductRepository.findAllByOrderId(orders.get(0).getId());
		assertEquals(1, orders.size());
		assertEquals(1, orderProducts.size());
		assertEquals(TestCommonConstants.USER_ID, orders.get(0).getUserId());
		assertEquals(OrderStatus.COMPLETED.name(), orders.get(0).getStatus().name());
		assertEquals(CraftTestConstants.ORDER_UNIQUE_REFERENCE_ID, orders.get(0).getUniqueReferenceId());
		assertEquals(CraftTestConstants.DRIVER_ID, orders.get(0).getCustomerId());
		assertEquals("DRIVER", orders.get(0).getCustomerType());
		assertEquals(orders.get(0).getId(), orderProducts.get(0).getOrderId());
		assertEquals(CraftTestConstants.PRODUCT_ID, orderProducts.get(0).getProductId());
		assertEquals(OrderProductStatus.COMPLETED.name(), orderProducts.get(0).getStatus().name());
		assertEquals(1, orderProducts.get(0).getQuantity());
		assertEquals(
				UUIDUtils.uuid5(CraftTestConstants.ORDER_UNIQUE_REFERENCE_ID + "_" + CraftTestConstants.PRODUCT_ID),
				orderProducts.get(0).getUniqueReferenceId());
	}

}