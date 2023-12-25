package code.shubham.commons.models;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class Event {

	private String eventName;

	private String eventType;

	private String data;

	private String uniqueReferenceId; // idempotency key

	private String userId;

	private Date createdAt;

	private String correlationId; // traceId, unique for each chain of events

	@Override
	public String toString() {
		return String.format("name: %s, type: %s, correlation_id: %s", eventName, eventType, correlationId);
	}

}
