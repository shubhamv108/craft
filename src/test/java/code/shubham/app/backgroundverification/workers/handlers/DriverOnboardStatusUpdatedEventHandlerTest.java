package code.shubham.app.backgroundverification.workers.handlers;

import code.shubham.test.AbstractSpringBootTest;
import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.models.Event;
import code.shubham.app.TestAppConstants;
import code.shubham.app.TestAppEventUtils;
import code.shubham.app.backgroundverification.dao.entities.BackgroundVerification;
import code.shubham.app.backgroundverification.dao.entities.BackgroundVerificationStatus;
import code.shubham.app.backgroundverification.dao.repositories.BackgroundVerificationRepository;
import code.shubham.app.driveronboard.dao.entities.DriverOnboard;
import code.shubham.app.driveronboard.dao.entities.DriverOnboardStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DriverOnboardStatusUpdatedEventHandlerTest extends AbstractSpringBootTest {

	@Autowired
	private DriverOnboardStatusUpdatedEventHandler handler;

	@Autowired
	private BackgroundVerificationRepository repository;

	@BeforeEach
	protected void setUp() {
		super.setUp();
		truncate("background_verification");
	}

	@AfterEach
	void tearDown() {
		truncate("background_verification");
	}

	@Test
	public void handle_Skip() {
		final DriverOnboard onboard = DriverOnboard.builder()
			.driverId(TestAppConstants.DRIVER_ID)
			.userId(TestCommonConstants.USER_ID)
			.status(DriverOnboardStatus.SHIPPING_OF_TRACKING_DEVICE)
			.clientReferenceId(TestAppConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
			.build();
		final Event event = TestAppEventUtils.getDriverOnboardStatusUpdatedEvent(onboard);

		this.handler.handle(event);

		final var created = this.repository
			.findByClientReferenceId(TestAppConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID);
		Assertions.assertFalse(created.isPresent());
	}

	@Test
	public void handle_with_existing_background_verification() {
		this.repository.save(BackgroundVerification.builder()
			.clientReferenceId(TestAppConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
			.userId(TestCommonConstants.USER_ID)
			.applicantType("DRIVER")
			.applicantId(TestAppConstants.DRIVER_ID)
			.status(BackgroundVerificationStatus.COMPLETED)
			.build());
		final DriverOnboard onboard = DriverOnboard.builder()
			.driverId(TestAppConstants.DRIVER_ID)
			.userId(TestCommonConstants.USER_ID)
			.status(DriverOnboardStatus.BACKGROUND_VERIFICATION)
			.clientReferenceId(TestAppConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
			.build();
		final Event event = TestAppEventUtils.getDriverOnboardStatusUpdatedEvent(onboard);

		this.handler.handle(event);

		final var created = this.repository
			.findByClientReferenceId(TestAppConstants.BACKGROUND_VERIFICATION_CLIENT_REFERENCE_ID);

		Assertions.assertTrue(created.isPresent());
		Assertions.assertNotNull(TestAppConstants.DRIVER_ID, created.get().getApplicantId());
		Assertions.assertEquals(TestAppConstants.DRIVER_ID, created.get().getApplicantId());
		Assertions.assertEquals(TestCommonConstants.USER_ID, created.get().getUserId());
		Assertions.assertEquals(BackgroundVerificationStatus.ONGOING.name(), created.get().getStatus().name());
	}

	@Test
	public void handle_Success() {
		final DriverOnboard onboard = DriverOnboard.builder()
			.driverId(TestAppConstants.DRIVER_ID)
			.userId(TestCommonConstants.USER_ID)
			.status(DriverOnboardStatus.BACKGROUND_VERIFICATION)
			.clientReferenceId(TestAppConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
			.build();
		final Event event = TestAppEventUtils.getDriverOnboardStatusUpdatedEvent(onboard);

		this.handler.handle(event);

		final var created = this.repository
			.findByClientReferenceId(TestAppConstants.BACKGROUND_VERIFICATION_CLIENT_REFERENCE_ID);

		Assertions.assertTrue(created.isPresent());
		Assertions.assertNotNull(TestAppConstants.DRIVER_ID, created.get().getApplicantId());
		Assertions.assertEquals(TestAppConstants.DRIVER_ID, created.get().getApplicantId());
		Assertions.assertEquals(TestCommonConstants.USER_ID, created.get().getUserId());
		Assertions.assertEquals(BackgroundVerificationStatus.ONGOING.name(), created.get().getStatus().name());
	}

}