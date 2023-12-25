package code.shubham.commons.kafka;

import code.shubham.commons.publishers.Publisher;
import code.shubham.commons.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class KafkaPublisher implements Publisher<CompletableFuture<SendResult<String, String>>> {

	@Value("${kafka.topic.name}")
	private String topicName;

	private final KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	public KafkaPublisher(final KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@Override
	public <T> CompletableFuture<SendResult<String, String>> send(final T object) {
		return this.send(this.topicName, JsonUtils.get(object));
	}

	@Override
	public <T> CompletableFuture<SendResult<String, String>> send(final String topicName, final T object) {
		return this.send(topicName, JsonUtils.get(object));
	}

	@Override
	public <T> CompletableFuture<SendResult<String, String>> send(final String topicName, final String data) {
		return this.kafkaTemplate.send(topicName, data);
	}

}