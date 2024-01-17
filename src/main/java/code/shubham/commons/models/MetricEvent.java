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
		builder.append(this.name);

		final StringBuilder labelBuilder = new StringBuilder();
		this.label.stream().forEach(pair -> {
			if (!labelBuilder.isEmpty())
				labelBuilder.append(',');
			labelBuilder.append(pair.getKey()).append("=").append(pair.getValue());
		});

		return builder.append(' ')
			.append(labelBuilder)
			.append(' ')
			.append(this.timestamp)
			.append(' ')
			.append(this.value)
			.toString();
	}

}
