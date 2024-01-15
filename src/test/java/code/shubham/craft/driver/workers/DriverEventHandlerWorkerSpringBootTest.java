package code.shubham.craft.driver.workers;

import code.shubham.commons.AbstractSpringBootTest;
import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.commons.models.Event;
import code.shubham.craft.CraftTestConstants;
import code.shubham.craft.TestEventUtils;
import code.shubham.craft.driver.cab.dao.repositories.CabRepository;
import code.shubham.craft.driver.dao.entities.Driver;
import code.shubham.craft.driver.dao.entities.DriverStatus;
import code.shubham.craft.driver.dao.repositories.DriverRepository;
import code.shubham.craft.driveronboard.TestCraftUtils;
import code.shubham.craft.driveronboard.dao.entities.DriverOnboard;
import code.shubham.craft.driveronboard.dao.entities.DriverOnboardStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestPropertySource(properties = "service.module=worker")
public class DriverEventHandlerWorkerSpringBootTest extends AbstractSpringBootTest {

	@Value("${driver.onboard.kafka.topic.name}")
	private String driverOnboardTopicName;

	@Value("${driver.worker.event.filters.eventname}")
	private Set<String> eventNameFilters;

	@Value("${driver.worker.event.failure.kafka.topic.name}")
	private String failureTopic;

	@Autowired
	private DriverRepository repository;

	@Autowired
	private CabRepository cabRepository;

	@Autowired
	private DriverEventHandlerWorker worker;

	@BeforeEach
	public void setUp() {
		super.setUp();
		truncate("drivers");
		truncate("cabs");
		TestCraftUtils.setContext();
	}

	@AfterEach
	void tearDown() {
		truncate("drivers");
		truncate("cabs");
	}

	@Test
	void process_skip() throws InterruptedException {
		final Driver existingDriver = this.repository.save(Driver.builder()
			.status(DriverStatus.ONBOARDING)
			.userId(TestCommonConstants.USER_ID)
			.drivingLicense(CraftTestConstants.DRIVING_LICENSE_NUMBER)
			.drivingLicenseName(CraftTestConstants.DRIVING_LICENSE_NAME)
			.build());
		final DriverOnboard onboard = DriverOnboard.builder()
			.driverId(existingDriver.getId())
			.userId(TestCommonConstants.USER_ID)
			.status(DriverOnboardStatus.SHIPPING_OF_TRACKING_DEVICE)
			.clientReferenceId(CraftTestConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
			.build();
		final Event event = TestEventUtils.getDriverOnboardStatusUpdatedEvent(onboard);

		this.worker.process(event);

		final Driver updatedDriver = this.repository.findById(existingDriver.getId()).get();
		assertEquals(DriverStatus.ONBOARDING.name(), updatedDriver.getStatus().name());
	}

	@Test
	void process_without_existing_driver() {
		final DriverOnboard onboard = DriverOnboard.builder()
			.driverId(CraftTestConstants.DRIVER_ID)
			.userId(TestCommonConstants.USER_ID)
			.status(DriverOnboardStatus.ONBOARDING_COMPLETED)
			.clientReferenceId(CraftTestConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
			.build();
		final Event event = TestEventUtils.getDriverOnboardStatusUpdatedEvent(onboard);

		final InvalidRequestException response = Assertions.assertThrows(InvalidRequestException.class,
				() -> this.worker.process(event));
		assertEquals("[\n" + "{\n" + "\t\"driverId\": [\n" + "\t\t\"no driver found for driverId: "
				+ CraftTestConstants.DRIVER_ID + "\"\n" + "\t]\n" + "\n" + "},]\n", response.toString());
	}

	@Test
	void process_Success() {
		final Driver existingDriver = this.repository.save(Driver.builder()
			.status(DriverStatus.ONBOARDING)
			.userId(TestCommonConstants.USER_ID)
			.drivingLicense(CraftTestConstants.DRIVING_LICENSE_NUMBER)
			.drivingLicenseName(CraftTestConstants.DRIVING_LICENSE_NAME)
			.build());
		final DriverOnboard onboard = DriverOnboard.builder()
			.driverId(existingDriver.getId())
			.userId(TestCommonConstants.USER_ID)
			.status(DriverOnboardStatus.ONBOARDING_COMPLETED)
			.clientReferenceId(CraftTestConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
			.build();
		final Event event = TestEventUtils.getDriverOnboardStatusUpdatedEvent(onboard);

		this.worker.process(event);

		final Driver updatedDriver = this.repository.findById(existingDriver.getId()).get();
		assertEquals(DriverStatus.ACTIVE.name(), updatedDriver.getStatus().name());
	}

	@Disabled
	@Test
	void onReceive_Success() throws InterruptedException {
		final Driver existingDriver = this.repository.save(Driver.builder()
			.status(DriverStatus.ONBOARDING)
			.userId(TestCommonConstants.USER_ID)
			.drivingLicense(CraftTestConstants.DRIVING_LICENSE_NUMBER)
			.drivingLicenseName(CraftTestConstants.DRIVING_LICENSE_NAME)
			.build());
		final DriverOnboard onboard = DriverOnboard.builder()
			.driverId(existingDriver.getId())
			.userId(TestCommonConstants.USER_ID)
			.status(DriverOnboardStatus.ONBOARDING_COMPLETED)
			.clientReferenceId(CraftTestConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
			.build();
		final Event event = TestEventUtils.getDriverOnboardStatusUpdatedEvent(onboard);

		this.kafkaPublisher.send(driverOnboardTopicName, event);

		Thread.sleep(1000);

		final Driver updatedDriver = this.repository.findById(existingDriver.getId()).get();
		assertEquals(DriverStatus.ACTIVE.name(), updatedDriver.getStatus().name());
	}

	@Test
	void getEventNameFilters() {
		assertEquals(this.worker.getEventNameFilters(), this.eventNameFilters);
	}

	@Test
	void getFailureTopic() {
		assertEquals(this.worker.getFailureTopic(), this.failureTopic);
	}

}