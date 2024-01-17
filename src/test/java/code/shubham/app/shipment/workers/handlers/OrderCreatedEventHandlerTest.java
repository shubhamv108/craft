package code.shubham.app.shipment.workers.handlers;

import code.shubham.test.AbstractSpringBootTest;
import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.models.Event;
import code.shubham.core.userprofile.dao.entities.UserProfile;
import code.shubham.core.userprofile.dao.repositories.UserProfileRepository;
import code.shubham.app.TestAppConstants;
import code.shubham.app.TestAppEventUtils;
import code.shubham.app.constants.EventName;
import code.shubham.app.order.dao.entities.Order;
import code.shubham.app.order.dao.entities.OrderStatus;
import code.shubham.app.shipment.dao.repositories.ShipmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderCreatedEventHandlerTest extends AbstractSpringBootTest {

	@Autowired
	private OrderCreatedEventHandler handler;

	@Autowired
	private ShipmentRepository repository;

	@Autowired
	private UserProfileRepository userProfileRepository;

	@BeforeEach
	public void setUp() {
		super.setUp();
		truncate("shipments");
		truncate("user_profiles");
	}

	@AfterEach
	void tearDown() {
		truncate("shipments");
		truncate("user_profiles");
	}

	@Test
	void handle_success() {
		this.userProfileRepository
			.save(UserProfile.builder().address("address").userId(TestCommonConstants.USER_ID).build());
		final Event event = TestAppEventUtils.getOrderEvent(Order.builder()
			.id(TestAppConstants.ORDER_ID)
			.status(OrderStatus.CREATED)
			.uniqueReferenceId(TestAppConstants.ORDER_UNIQUE_REFERENCE_ID)
			.userId(TestCommonConstants.USER_ID)
			.build(), EventName.OrderCreated);

		this.handler.handle(event);

		final var shipment = this.repository.findByUniqueReferenceId(TestAppConstants.SHIPMENT_UNIQUE_REFERENCE_ID);
		assertTrue(shipment.isPresent());
		assertEquals(TestAppConstants.ORDER_ID, shipment.get().getOrderId());
	}

}