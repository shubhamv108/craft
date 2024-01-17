package code.shubham.app.driveronboard.workers;

import code.shubham.test.AbstractSpringBootTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = "service.module=worker")
class DriverOnboardEventHandlerWorkerTest extends AbstractSpringBootTest {

	@Value("${driver.onboard.worker.event.failure.kafka.topic.name}")
	private String failureTopic;

	@Autowired
	private DriverOnboardEventHandlerWorker worker;

	@Test
	void getFailureTopic() {
		Assertions.assertEquals(this.worker.getFailureTopic(), this.failureTopic);
	}

}