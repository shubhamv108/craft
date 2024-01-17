package code.shubham.commons.models;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Stream;

public class Label {

	private final Collection<Pair<String, String>> labels;

	private Label(final Collection<Pair<String, String>> labels) {
		this.labels = labels;
	}

	public Stream<Pair<String, String>> stream() {
		return labels.stream();
	}

	public static LabelBuilder builder() {
		return new LabelBuilder();
	}

	public static class LabelBuilder {

		private String key;

		private String value;

		private final Collection<Pair<String, String>> labels = new LinkedList<>();

		public LabelBuilder add() {
			this.labels.add(Pair.of(key, value));
			this.key = null;
			this.value = null;
			return this;
		}

		public LabelBuilder key(final String key) {
			this.key = key;
			return this;
		}

		public LabelBuilder value(final String value) {
			this.value = value;
			return this;
		}

		public Label build() {
			this.add();
			return new Label(this.labels);
		}

	}

}
