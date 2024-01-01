package code.shubham.commons;

import code.shubham.commons.models.Event;
import code.shubham.commons.utils.JsonUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.server.util.Json;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Component
public class TestKafkaConsumer {

	@Value(value = "${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	public List<Event> poll(final String topic, final int count) {
		return this.poll(topic, count);
	}

	public List<Event> poll(final String topic, final int count, final long timeout) {
		final Properties properties = new Properties();
		properties.put("bootstrap.servers", this.bootstrapServers);
		properties.put("group.id", "test");
		properties.put("enable.auto.commit", "true");
		properties.put("auto.commit.interval.ms", "1000");
		properties.put("session.timeout.ms", "30000");
		properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

		consumer.subscribe(Arrays.asList(topic));

		final List<Event> events = new ArrayList<>();
		while (events.size() < count) {
			final ConsumerRecords<String, String> records = consumer.poll(timeout);
			for (final ConsumerRecord<String, String> record : records)
				events.add(JsonUtils.as(record.value().toString(), Event.class));
		}
		consumer.unsubscribe();
		return events;
	}

}
