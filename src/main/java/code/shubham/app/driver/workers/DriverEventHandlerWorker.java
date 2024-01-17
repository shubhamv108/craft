package code.shubham.app.driver.workers;

import code.shubham.commons.models.Event;
import code.shubham.commons.utils.JsonUtils;
import code.shubham.commons.workers.AbstractWorker;
import code.shubham.app.driver.dao.entities.DriverStatus;
import code.shubham.app.driver.service.DriverService;
import code.shubham.app.driveronboardmodels.DriverOnboardStatusUpdatedEventData;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "worker")
public class DriverEventHandlerWorker extends AbstractWorker {

	@Value("${driver.worker.event.filters.eventname}")
	private Set<String> eventNameFilters;

	@Value("${driver.worker.event.failure.kafka.topic.name}")
	private String failureTopic;

	private final DriverService service;

	@Autowired
	public DriverEventHandlerWorker(final DriverService service) {
		this.service = service;
	}

	@Override
	protected void process(final Event event) {
		final DriverOnboardStatusUpdatedEventData data = JsonUtils.as(event.getData(),
				DriverOnboardStatusUpdatedEventData.class);
		if (!"ONBOARDING_COMPLETED".equals(data.getDriverOnboard().getStatus())) {
			log.info("[SKIP] Event: {}", event);
			return;
		}
		this.service.updateStatus(data.getDriverOnboard().getDriverId(), DriverStatus.ONBOARDING);
	}

	@Override
	@KafkaListener(topics = { "${driver.worker.driver.onboard.event.kafka.topic.name}" },
			groupId = "${driver.worker.kafka.group.id}")
	public void onReceive(final ConsumerRecord<?, ?> consumerRecord, final Acknowledgment acknowledgment) {
		this.work(consumerRecord, acknowledgment);
	}

	@Override
	protected Set<String> getEventNameFilters() {
		return this.eventNameFilters;
	}

	@Override
	protected String getFailureTopic() {
		return this.failureTopic;
	}

}
