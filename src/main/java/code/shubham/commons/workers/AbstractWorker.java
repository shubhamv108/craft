package code.shubham.commons.workers;

import code.shubham.commons.contexts.CorrelationIDContext;
import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.commons.exceptions.SentryCaptureException;
import code.shubham.commons.kafka.KafkaPublisher;
import code.shubham.commons.models.Event;
import code.shubham.commons.utils.JsonUtils;
import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.support.Acknowledgment;

import java.util.Set;

@Slf4j
public abstract class AbstractWorker {

	@Autowired
	protected ApplicationContext applicationContext;

	@Autowired
	protected KafkaPublisher kafkaPublisher;

	protected abstract void process(final Event event);

	public void work(final ConsumerRecord<?, ?> consumerRecord, final Acknowledgment acknowledgment) {
		String data = null;
		Event event = null;
		try {
			data = consumerRecord.value().toString();
			event = JsonUtils.as(data, Event.class);
			if (!this.getEventNameFilters().contains(event.getEventName())) {
				log.info("[SKIP] Received unfiltered event: {}", event);
				return;
			}

			log.info(String.format("[STARTED]: Processing event %s", event));

			CorrelationIDContext.set(event.getCorrelationId());
			UserIDContextHolder.set(event.getUserId());

			log.info("[Received] Event data: {}", event.getData());
			this.process(event);

			log.info(String.format("[SUCCESS]: Processing event %s", event));
		}
		catch (Exception exception) {
			log.info(String.format("[FAIL]: Processing event %s, Reason: %s", event, exception.getMessage()));
			Sentry.captureException(new SentryCaptureException(exception, data));
			this.kafkaPublisher.send(this.getFailureTopic(), data);
		}
		finally {
			log.info(String.format("[COMPLETED]: Processing event %s", event));
			acknowledgment.acknowledge();
			CorrelationIDContext.clear();
			UserIDContextHolder.clear();
		}
	}

	public abstract void onReceive(ConsumerRecord<?, ?> consumerRecord, Acknowledgment acknowledgment);

	protected abstract Set<String> getEventNameFilters();

	protected abstract String getFailureTopic();

}
