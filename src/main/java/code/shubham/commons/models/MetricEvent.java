package code.shubham.commons.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MetricEvent {

	private String name;

	private Label label;

	private long timestamp;

	private double value;

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(this.name).append(" ");

		this.label.stream().forEach(pair -> builder.append(pair.getKey()).append("=").append(pair.getValue()));

		builder.append(" ").append(this.timestamp).append(this.value);
		return builder.toString();
	}

}
