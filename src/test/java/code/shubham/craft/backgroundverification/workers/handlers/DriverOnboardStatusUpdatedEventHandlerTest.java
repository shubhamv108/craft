package code.shubham.craft.backgroundverification.workers.handlers;

import code.shubham.commons.AbstractSpringBootTest;
import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.models.Event;
import code.shubham.craft.CraftTestConstants;
import code.shubham.craft.TestEventUtils;
import code.shubham.craft.backgroundverification.dao.entities.BackgroundVerification;
import code.shubham.craft.backgroundverification.dao.entities.BackgroundVerificationStatus;
import code.shubham.craft.backgroundverification.dao.repositories.BackgroundVerificationRepository;
import code.shubham.craft.driveronboard.dao.entities.DriverOnboard;
import code.shubham.craft.driveronboard.dao.entities.DriverOnboardStatus;
import code.shubham.craft.driveronboard.dao.repositories.DriverOnboardBackgroundVerificationRepository;
import code.shubham.craft.driveronboard.dao.repositories.DriverOnboardRepository;
import code.shubham.craft.driveronboard.workers.handlers.BackgroundVerificationStatusUpdatedEventHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

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
			.driverId(CraftTestConstants.DRIVER_ID)
			.userId(TestCommonConstants.USER_ID)
			.status(DriverOnboardStatus.SHIPPING_OF_TRACKING_DEVICE)
			.clientReferenceId(CraftTestConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
			.build();
		final Event event = TestEventUtils.getDriverOnboardStatusUpdatedEvent(onboard);

		this.handler.handle(event);

		final var created = this.repository
			.findByClientReferenceId(CraftTestConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID);
		Assertions.assertFalse(created.isPresent());
	}

	@Test
	public void handle_with_existing_background_verification() {
		this.repository.save(BackgroundVerification.builder()
			.clientReferenceId(CraftTestConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
			.userId(TestCommonConstants.USER_ID)
			.applicantType("DRIVER")
			.applicantId(CraftTestConstants.DRIVER_ID)
			.status(BackgroundVerificationStatus.COMPLETED)
			.build());
		final DriverOnboard onboard = DriverOnboard.builder()
			.driverId(CraftTestConstants.DRIVER_ID)
			.userId(TestCommonConstants.USER_ID)
			.status(DriverOnboardStatus.BACKGROUND_VERIFICATION)
			.clientReferenceId(CraftTestConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
			.build();
		final Event event = TestEventUtils.getDriverOnboardStatusUpdatedEvent(onboard);

		this.handler.handle(event);

		final var created = this.repository
			.findByClientReferenceId(CraftTestConstants.BACKGROUND_VERIFICATION_CLIENT_REFERENCE_ID);

		Assertions.assertTrue(created.isPresent());
		Assertions.assertNotNull(CraftTestConstants.DRIVER_ID, created.get().getApplicantId());
		Assertions.assertEquals(CraftTestConstants.DRIVER_ID, created.get().getApplicantId());
		Assertions.assertEquals(TestCommonConstants.USER_ID, created.get().getUserId());
		Assertions.assertEquals(BackgroundVerificationStatus.ONGOING.name(), created.get().getStatus().name());
	}

	@Test
	public void handle_Success() {
		final DriverOnboard onboard = DriverOnboard.builder()
			.driverId(CraftTestConstants.DRIVER_ID)
			.userId(TestCommonConstants.USER_ID)
			.status(DriverOnboardStatus.BACKGROUND_VERIFICATION)
			.clientReferenceId(CraftTestConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
			.build();
		final Event event = TestEventUtils.getDriverOnboardStatusUpdatedEvent(onboard);

		this.handler.handle(event);

		final var created = this.repository
			.findByClientReferenceId(CraftTestConstants.BACKGROUND_VERIFICATION_CLIENT_REFERENCE_ID);

		Assertions.assertTrue(created.isPresent());
		Assertions.assertNotNull(CraftTestConstants.DRIVER_ID, created.get().getApplicantId());
		Assertions.assertEquals(CraftTestConstants.DRIVER_ID, created.get().getApplicantId());
		Assertions.assertEquals(TestCommonConstants.USER_ID, created.get().getUserId());
		Assertions.assertEquals(BackgroundVerificationStatus.ONGOING.name(), created.get().getStatus().name());
	}

}