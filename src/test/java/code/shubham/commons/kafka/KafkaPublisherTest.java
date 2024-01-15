package code.shubham.commons.kafka;

import code.shubham.commons.AbstractSpringBootTest;
import code.shubham.commons.TestKafkaConsumer;
import code.shubham.commons.models.Event;
import code.shubham.craft.TestEventUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestPropertySource(properties = "kafka.topic.name=test")
class KafkaPublisherTest extends AbstractSpringBootTest {

	@Autowired
	private KafkaPublisher publisher;

	private final String topicName = "test";

	@PostConstruct
	public void allSetUp() {
		this.kafkaConsumer = new TestKafkaConsumer(this.topicName);
	}

	@PreDestroy
	public void tearDownAll() {
		this.kafkaConsumer.destroy();
	}

	@BeforeEach
	protected void setUp() {
		super.setUp();
		this.kafkaConsumer.purge(this.topicName);
	}

	@AfterEach
	void tearDown() {
		this.kafkaConsumer.purge(this.topicName);
	}

	@Test
	void test_send() {
		final Event event = TestEventUtils.getEmptyEvent();

		this.publisher.send(event);

		assertEquals(event.getEventName(), this.kafkaConsumer.poll(1).get(0).getEventName());
	}

}