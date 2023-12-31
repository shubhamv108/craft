package code.shubham.craft.driveronboard.workers.handlers;

import code.shubham.craft.TestEventUtils;
import code.shubham.commons.AbstractTest;
import code.shubham.commons.CommonTestConstants;
import code.shubham.craft.CraftTestConstants;
import code.shubham.craft.backgroundverification.dao.entities.BackgroundVerification;
import code.shubham.craft.backgroundverification.dao.entities.BackgroundVerificationStatus;
import code.shubham.craft.driveronboard.dao.entities.DriverOnboard;
import code.shubham.craft.driveronboard.dao.entities.DriverOnboardBackgroundVerification;
import code.shubham.craft.driveronboard.dao.entities.DriverOnboardStatus;
import code.shubham.craft.driveronboard.dao.repositories.DriverOnboardBackgroundVerificationRepository;
import code.shubham.craft.driveronboard.dao.repositories.DriverOnboardRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BackgroundVerificationStatusUpdatedEventHandlerTest extends AbstractTest {

	@Autowired
	private BackgroundVerificationStatusUpdatedEventHandler handler;

	@Autowired
	private DriverOnboardRepository repository;

	@Autowired
	private DriverOnboardBackgroundVerificationRepository backgroundVerificationRepository;

	@BeforeEach
	protected void setUp() {
		super.setUp();
		truncate("driver_onboard");
		truncate("driver_onboard__background_verifications_mapping");
	}

	@AfterEach
	void tearDown() {
		truncate("driver_onboard");
		truncate("driver_onboard__background_verifications_mapping");
	}

	@Test
	void handle_Success() {
		final DriverOnboard driverOnboard = this.repository.save(DriverOnboard.builder()
			.driverId(CraftTestConstants.DRIVER_ID)
			.status(DriverOnboardStatus.BACKGROUND_VERIFICATION)
			.userId(CommonTestConstants.USER_ID)
			.clientReferenceId(CraftTestConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
			.build());
		this.backgroundVerificationRepository.save(DriverOnboardBackgroundVerification.builder()
			.driverOnboardId(driverOnboard.getId())
			.backgroundVerificationReferenceId(CraftTestConstants.BACKGROUND_VERIFICATION_CLIENT_REFERENCE_ID)
			.build());
		final BackgroundVerification backgroundVerification = BackgroundVerification.builder()
			.status(BackgroundVerificationStatus.COMPLETED)
			.clientReferenceId(CraftTestConstants.BACKGROUND_VERIFICATION_CLIENT_REFERENCE_ID)
			.applicantType(CraftTestConstants.APPLICANT_TYPE_DRIVER)
			.userId(CommonTestConstants.USER_ID)
			.build();
		DriverOnboard updated = (DriverOnboard) this.handler
			.handle(TestEventUtils.getBackgoundVerificationStatusUpdatedEvent(backgroundVerification));

		Assertions.assertEquals(updated.getStatus().name(), DriverOnboardStatus.SHIPPING_OF_TRACKING_DEVICE.name());
		Assertions.assertEquals(this.repository.findById(driverOnboard.getId()).get().getStatus().name(),
				DriverOnboardStatus.SHIPPING_OF_TRACKING_DEVICE.name());

	}

}