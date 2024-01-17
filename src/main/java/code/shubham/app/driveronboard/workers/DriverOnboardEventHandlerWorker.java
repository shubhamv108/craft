package code.shubham.app.driveronboard.workers;

import code.shubham.commons.workers.AbstractEventHandlerWorker;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "worker")
public class DriverOnboardEventHandlerWorker extends AbstractEventHandlerWorker {

	@Value("${driver.onboard.worker.events.filters.eventname}")
	private Set<String> eventNameFilters;

	@Value("${driver.onboard.worker.event.failure.kafka.topic.name}")
	private String failureTopic;

	@Override
	@KafkaListener(
			topics = { "${driver.onboard.worker.driver.kafka.topic.name}",
					"${driver.onboard.worker.background.verification.event.kafka.topic.name}",
					"${driver.onboard.worker.order.event.kafka.topic.name}" },
			groupId = "${driver.onboard.worker.kafka.group.id}")
	public void onReceive(final ConsumerRecord<?, ?> consumerRecord, final Acknowledgment acknowledgment) {
		this.work(consumerRecord, acknowledgment);
	}

	@Override
	protected Set<String> getEventNameFilters() {
		return this.eventNameFilters;
	}

	@Override
	protected String getFailureTopic() {
		return failureTopic;
	}

}
