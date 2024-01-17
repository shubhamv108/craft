package code.shubham.app.driveronboard.workers.handlers;

import code.shubham.test.AbstractSpringBootTest;
import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.models.Event;
import code.shubham.app.TestAppConstants;
import code.shubham.app.TestAppEventUtils;
import code.shubham.app.constants.EventName;
import code.shubham.app.driveronboard.dao.entities.DriverOnboard;
import code.shubham.app.driveronboard.dao.entities.DriverOnboardOrder;
import code.shubham.app.driveronboard.dao.entities.DriverOnboardStatus;
import code.shubham.app.driveronboard.dao.repositories.DriverOnboardOrderRepository;
import code.shubham.app.driveronboard.dao.repositories.DriverOnboardRepository;
import code.shubham.app.order.dao.entities.Order;
import code.shubham.app.order.dao.entities.OrderStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class OrderStatusUpdatedEventHandlerTest extends AbstractSpringBootTest {

	@Autowired
	private OrderStatusUpdatedEventHandler handler;

	@Autowired
	private DriverOnboardRepository repository;

	@Autowired
	private DriverOnboardOrderRepository driverOnboardOrderRepository;

	@BeforeEach
	protected void setUp() {
		super.setUp();
		truncate("driver_onboard");
		truncate("driver_onboard__orders_mapping");
	}

	@AfterEach
	void tearDown() {
		truncate("driver_onboard");
		truncate("driver_onboard__orders_mapping");
	}

	@Test
	void handle_Success() {
		final DriverOnboard driverOnboard = this.repository.save(DriverOnboard.builder()
			.driverId(TestAppConstants.DRIVER_ID)
			.status(DriverOnboardStatus.SHIPPING_OF_TRACKING_DEVICE)
			.userId(TestCommonConstants.USER_ID)
			.clientReferenceId(TestAppConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
			.build());

		this.driverOnboardOrderRepository.save(DriverOnboardOrder.builder()
			.driverOnboardId(driverOnboard.getId())
			.orderReferenceId(TestAppConstants.ORDER_UNIQUE_REFERENCE_ID)
			.build());

		final Event event = TestAppEventUtils.getOrderEvent(Order.builder()
			.uniqueReferenceId(TestAppConstants.ORDER_UNIQUE_REFERENCE_ID)
			.customerType("DRIVER")
			.userId(TestCommonConstants.USER_ID)
			.status(OrderStatus.COMPLETED)
			.build(), EventName.OrderStatusUpdated);

		final DriverOnboard response = (DriverOnboard) this.handler.handle(event);

		Assertions.assertEquals(response.getStatus().name(), DriverOnboardStatus.ONBOARDING_COMPLETED.name());
		Assertions.assertEquals(this.repository.findById(driverOnboard.getId()).get().getStatus().name(),
				DriverOnboardStatus.ONBOARDING_COMPLETED.name());
	}

	@Test
	void handle_with_order_in_created_status() {
		final Event event = TestAppEventUtils.getOrderEvent(Order.builder()
			.uniqueReferenceId(TestAppConstants.ORDER_UNIQUE_REFERENCE_ID)
			.customerType("DRIVER")
			.userId(TestCommonConstants.USER_ID)
			.status(OrderStatus.CREATED)
			.build(), EventName.OrderStatusUpdated);

		Assertions.assertNull(this.handler.handle(event));
	}

}