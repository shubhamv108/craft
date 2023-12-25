package code.shubham.commons.publishers;

import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

public interface Publisher<R> {

	<T> R send(String topicName, T object);

	<T> R send(T object);

	<T> CompletableFuture<SendResult<String, String>> send(String topicName, String data);

}
