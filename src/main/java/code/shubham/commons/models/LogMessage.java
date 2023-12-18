package code.shubham.commons.models;

import code.shubham.craft.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.HashMap;
import java.util.Map;

@Builder
@AllArgsConstructor
public class LogMessage {

	private final String message;

	private Map<String, Object> metrics;

	public static String of(final String message, final Object... values) {
		return LogMessage.builder().message(String.format("Persisted event for userId: %s", values)).build().toString();
	}

	public void putMetric(final String key, final Object value) {
		if (metrics == null)
			this.metrics = new HashMap<>();
		this.metrics.put(key, value);
	}

	@Override
	public String toString() {
		return JsonUtils.get(this);
	}

}
