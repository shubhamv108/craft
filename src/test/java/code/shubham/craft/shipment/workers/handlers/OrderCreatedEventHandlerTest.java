package code.shubham.craft.shipment.workers.handlers;

import code.shubham.commons.AbstractSpringBootTest;
import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.models.Event;
import code.shubham.core.userprofile.dao.entities.UserProfile;
import code.shubham.core.userprofile.dao.repositories.UserProfileRepository;
import code.shubham.craft.CraftTestConstants;
import code.shubham.craft.TestEventUtils;
import code.shubham.craft.constants.EventName;
import code.shubham.craft.order.dao.entities.Order;
import code.shubham.craft.order.dao.entities.OrderStatus;
import code.shubham.craft.shipment.dao.repositories.ShipmentRepository;
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
		final Event event = TestEventUtils.getOrderEvent(Order.builder()
			.id(CraftTestConstants.ORDER_ID)
			.status(OrderStatus.CREATED)
			.uniqueReferenceId(CraftTestConstants.ORDER_UNIQUE_REFERENCE_ID)
			.userId(TestCommonConstants.USER_ID)
			.build(), EventName.OrderCreated);

		this.handler.handle(event);

		final var shipment = this.repository.findByUniqueReferenceId(CraftTestConstants.SHIPMENT_UNIQUE_REFERENCE_ID);
		assertTrue(shipment.isPresent());
		assertEquals(CraftTestConstants.ORDER_ID, shipment.get().getOrderId());
	}

}