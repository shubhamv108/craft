package code.shubham.app.backgroundverification.workers;

import code.shubham.test.AbstractSpringBootTest;
import code.shubham.app.driveronboard.TestCraftUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;

import java.util.Set;

@TestPropertySource(properties = "service.module=worker")
class BackgroundVerificationWorkerEventHandlerWorkerTest extends AbstractSpringBootTest {

	@Value("${background.verification.worker.event.filters.eventname}")
	private Set<String> eventNameFilters;

	@Value("${background.verification.worker.failure.kafka.topic.name}")
	private String failureTopic;

	@Autowired
	private BackgroundVerificationWorkerEventHandlerWorker worker;

	@BeforeEach
	public void setUp() {
		super.setUp();
		truncate("background_verification");
		TestCraftUtils.setContext();
	}

	@AfterEach
	void tearDown() {
		truncate("background_verification");
	}

	@Test
	void getEventNameFilters() {
		Assertions.assertEquals(this.eventNameFilters, this.worker.getEventNameFilters());
	}

	@Test
	void getFailureTopic() {
		Assertions.assertEquals(this.failureTopic, this.worker.getFailureTopic());
	}

}