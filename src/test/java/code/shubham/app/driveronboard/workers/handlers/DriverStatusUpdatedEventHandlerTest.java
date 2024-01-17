package code.shubham.app.driveronboard.workers.handlers;

import code.shubham.test.AbstractSpringBootTest;
import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.models.Event;
import code.shubham.app.TestAppConstants;
import code.shubham.app.TestAppEventUtils;
import code.shubham.app.driver.dao.entities.Driver;
import code.shubham.app.driver.dao.entities.DriverStatus;
import code.shubham.app.driveronboard.dao.entities.DriverOnboard;
import code.shubham.app.driveronboard.dao.entities.DriverOnboardStatus;
import code.shubham.app.driveronboard.dao.repositories.DriverOnboardRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DriverStatusUpdatedEventHandlerTest extends AbstractSpringBootTest {

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
			.driverId(TestAppConstants.DRIVER_ID)
			.status(DriverOnboardStatus.BACKGROUND_VERIFICATION)
			.userId(TestCommonConstants.USER_ID)
			.clientReferenceId(TestAppConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
			.build());
		final Event event = TestAppEventUtils.getDriverStatusUpdatedEvent(Driver.builder()
			.id(TestAppConstants.DRIVER_ID)
			.status(DriverStatus.ONBOARDING)
			.userId(TestCommonConstants.USER_ID)
			.build());

		final DriverOnboard response = (DriverOnboard) this.handler.handle(event);

		Assertions.assertEquals(response.getStatus().name(), DriverOnboardStatus.BACKGROUND_VERIFICATION.name());
		Assertions.assertEquals(this.repository.findById(driverOnboard.getId()).get().getStatus().name(),
				DriverOnboardStatus.BACKGROUND_VERIFICATION.name());
	}

}