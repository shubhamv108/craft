package code.shubham.craft.backgroundverification.workers;

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
public class BackgroundVerificationWorkerEventHandlerWorker extends AbstractEventHandlerWorker {

	@Value("${background.verification.worker.event.filters.eventname}")
	private Set<String> eventNameFilters;

	@Value("${background.verification.worker.failure.kafka.topic.name}")
	private String failureTopic;

	@Override
	@KafkaListener(topics = "${background.verification.worker.driver.onboard.event.kafka.topic.name}",
			groupId = "${background.verification.worker.kafka.group.id}")
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
