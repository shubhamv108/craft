package code.shubham.app.shipment.workers;

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
public class ShipmentEventHandlerWorker extends AbstractEventHandlerWorker {

	@Value("${shipment.worker.event.filters.eventname}")
	private Set<String> eventNameFilters;

	@Value("${shipment.worker.failure.kafka.topic.name}")
	private String failureTopic;

	@Override
	@KafkaListener(topics = "${shipment.worker.order.event.kafka.topic.name}",
			groupId = "${shipment.worker.kafka.group.id}")
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
