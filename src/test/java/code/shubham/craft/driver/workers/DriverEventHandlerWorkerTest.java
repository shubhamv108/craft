package code.shubham.craft.driver.workers;

import code.shubham.commons.AbstractTest;
import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.models.Event;
import code.shubham.craft.CraftTestConstants;
import code.shubham.craft.TestEventUtils;
import code.shubham.craft.driver.service.DriverService;
import code.shubham.craft.driveronboard.dao.entities.DriverOnboard;
import code.shubham.craft.driveronboard.dao.entities.DriverOnboardStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class DriverEventHandlerWorkerTest extends AbstractTest {

	@MockBean
	private DriverService driverService;

	@InjectMocks
	private DriverEventHandlerWorker worker;

	@Test
	void process_skip() {
		final DriverOnboard onboard = DriverOnboard.builder()
			.driverId(CraftTestConstants.DRIVER_ID)
			.userId(TestCommonConstants.USER_ID)
			.status(DriverOnboardStatus.SHIPPING_OF_TRACKING_DEVICE)
			.clientReferenceId(CraftTestConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
			.build();
		final Event event = TestEventUtils.getDriverOnboardStatusUpdatedEvent(onboard);

		this.worker.process(event);
		String output = outContent.toString();

		assertTrue(output.endsWith(String.format("[SKIP] Event: %s\n", event)));
	}

}
