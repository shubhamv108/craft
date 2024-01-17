package code.shubham.app.driveronboard.workers.handlers;

import code.shubham.app.TestAppEventUtils;
import code.shubham.test.AbstractSpringBootTest;
import code.shubham.commons.TestCommonConstants;
import code.shubham.app.TestAppConstants;
import code.shubham.app.backgroundverification.dao.entities.BackgroundVerification;
import code.shubham.app.backgroundverification.dao.entities.BackgroundVerificationStatus;
import code.shubham.app.driveronboard.dao.entities.DriverOnboard;
import code.shubham.app.driveronboard.dao.entities.DriverOnboardBackgroundVerification;
import code.shubham.app.driveronboard.dao.entities.DriverOnboardStatus;
import code.shubham.app.driveronboard.dao.repositories.DriverOnboardBackgroundVerificationRepository;
import code.shubham.app.driveronboard.dao.repositories.DriverOnboardRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BackgroundVerificationStatusUpdatedEventHandlerTest extends AbstractSpringBootTest {

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
			.driverId(TestAppConstants.DRIVER_ID)
			.status(DriverOnboardStatus.BACKGROUND_VERIFICATION)
			.userId(TestCommonConstants.USER_ID)
			.clientReferenceId(TestAppConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
			.build());
		this.backgroundVerificationRepository.save(DriverOnboardBackgroundVerification.builder()
			.driverOnboardId(driverOnboard.getId())
			.backgroundVerificationReferenceId(TestAppConstants.BACKGROUND_VERIFICATION_CLIENT_REFERENCE_ID)
			.build());
		final BackgroundVerification backgroundVerification = BackgroundVerification.builder()
			.status(BackgroundVerificationStatus.COMPLETED)
			.clientReferenceId(TestAppConstants.BACKGROUND_VERIFICATION_CLIENT_REFERENCE_ID)
			.applicantType(TestAppConstants.APPLICANT_TYPE_DRIVER)
			.userId(TestCommonConstants.USER_ID)
			.build();
		DriverOnboard updated = (DriverOnboard) this.handler
			.handle(TestAppEventUtils.getBackgoundVerificationStatusUpdatedEvent(backgroundVerification));

		Assertions.assertEquals(updated.getStatus().name(), DriverOnboardStatus.SHIPPING_OF_TRACKING_DEVICE.name());
		Assertions.assertEquals(this.repository.findById(driverOnboard.getId()).get().getStatus().name(),
				DriverOnboardStatus.SHIPPING_OF_TRACKING_DEVICE.name());

	}

}