package code.shubham.craft.driveronboard.workers.handlers;

import code.shubham.commons.AbstractTest;
import code.shubham.commons.CommonTestConstants;
import code.shubham.commons.models.Event;
import code.shubham.craft.CraftTestConstants;
import code.shubham.craft.TestEventUtils;
import code.shubham.craft.driver.dao.entities.Driver;
import code.shubham.craft.driver.dao.entities.DriverStatus;
import code.shubham.craft.driveronboard.dao.entities.DriverOnboard;
import code.shubham.craft.driveronboard.dao.entities.DriverOnboardStatus;
import code.shubham.craft.driveronboard.dao.repositories.DriverOnboardRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DriverStatusUpdatedEventHandlerTest extends AbstractTest {

	@Autowired
	private DriverStatusUpdatedEventHandler handler;

	@Autowired
	private DriverOnboardRepository repository;

	@BeforeEach
	protected void setUp() {
		super.setUp();
		truncate("driver_onboard");
	}

	@AfterEach
	void tearDown() {
		truncate("driver_onboard");
	}

	@Test
	void handle() {
		final DriverOnboard driverOnboard = this.repository.save(DriverOnboard.builder()
			.driverId(CraftTestConstants.DRIVER_ID)
			.status(DriverOnboardStatus.BACKGROUND_VERIFICATION)
			.userId(CommonTestConstants.USER_ID)
			.clientReferenceId(CraftTestConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
			.build());
		final Event event = TestEventUtils.getDriverStatusUpdatedEvent(Driver.builder()
			.id(CraftTestConstants.DRIVER_ID)
			.status(DriverStatus.ONBOARDING)
			.userId(CommonTestConstants.USER_ID)
			.build());

		final DriverOnboard response = (DriverOnboard) this.handler.handle(event);

		Assertions.assertEquals(response.getStatus().name(), DriverOnboardStatus.BACKGROUND_VERIFICATION.name());
		Assertions.assertEquals(this.repository.findById(driverOnboard.getId()).get().getStatus().name(),
				DriverOnboardStatus.BACKGROUND_VERIFICATION.name());
	}

}