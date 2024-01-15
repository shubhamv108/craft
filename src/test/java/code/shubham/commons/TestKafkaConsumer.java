package code.shubham.commons;

import code.shubham.commons.models.Event;
import code.shubham.commons.utils.JsonUtils;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class TestKafkaConsumer {

	private final Admin admin;

	private String topic;

	public TestKafkaConsumer(final String topic) {
		this.topic = topic;
		final Properties properties = new Properties();
		properties.put("bootstrap.servers", "localhost:29092");
		this.admin = Admin.create(properties);
		this.purge(topic);
	}

	public List<Event> poll(final int count) {
		return this.poll(count, 10, Event.class);
	}

	public <T> List<T> poll(final int count, final Class<T> clazz) {
		return this.poll(count, 10, clazz);
	}

	public <T> List<T> poll(final int count, final long timeout, Class<T> clazz) {
		final Properties properties = new Properties();
		properties.put("bootstrap.servers", "localhost:29092");
		properties.put("group.id", "test");
		properties.put("enable.auto.commit", "false");
		properties.put("auto.commit.interval.ms", "1000");
		properties.put("session.timeout.ms", "30000");
		properties.put("auto.offset.reset", "earliest");
		properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		final KafkaConsumer consumer = new KafkaConsumer<>(properties);
		consumer.subscribe(Arrays.asList(topic));
		final List<T> events = new ArrayList<>();
		while (events.size() < count) {
			final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(timeout));
			for (ConsumerRecord<String, String> record : records)
				events.add(JsonUtils.as(record.value().toString(), clazz));
		}
		// consumer.commitSync();
		consumer.unsubscribe();
		return events;
	}

	public void purge(final String topicName) {
		this.admin.deleteTopics(List.of(topicName));
	}

	public void destroy() {
		this.admin.close();
	}

}
