package code.shubham.commons.validators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractValidator<OBJECT> implements IValidator<OBJECT> {

	private final Collection<Map<String, Collection<String>>> errorMessagesList = new ArrayList<>();

	private Map<String, Collection<String>> messages = new LinkedHashMap<>();

	@SuppressWarnings("all")
	protected boolean putMessage(final String messageKey, final String messageValueFormat) {
		return this.putMessage(messageKey, messageValueFormat, null);
	}

	@SuppressWarnings("all")
	protected boolean putMessage(final String messageKey, final String messageValueFormat,
			final Object... messageValueArguments) {
		String messageValue = String.format(messageValueFormat, messageValueArguments);
		Collection<String> messagesValues = this.messages.get(messageKey);
		if (messagesValues == null)
			this.messages.put(messageKey, messagesValues = new ArrayList<>());
		return messagesValues.add(messageValue);
	}

	// protected boolean putMessages(final String messageKey, final Collection<String>
	// messageValue) {
	// Collection<String> messagesValues = this.messages.get(messageKey);
	// if (messagesValues == null)
	// this.messages.put(messageKey, messagesValues = new ArrayList<>());
	// return messagesValues.addAll(messageValue);
	// }

	@Override
	public boolean hasMessages() {
		return !this.getMessages().isEmpty();
	}

	@Override
	public Map<String, Collection<String>> getResult() {
		Map<String, Collection<String>> copy = new LinkedHashMap<>();
		this.messages.forEach((k, v) -> copy.put(k, new java.util.ArrayList<String>(v)));
		return copy;
	}

	private Map<String, Collection<String>> getMessages() {
		return this.messages;
	}

	// @Override
	// public String toString() {
	// StringBuilder builder = new StringBuilder("[\n");
	// errorMessagesList.stream().map(errorMessagesMap -> {
	// StringBuilder mapBBuilder = new StringBuilder("{\n");
	// errorMessagesMap.forEach((k, v) -> {
	// mapBBuilder.append("\t").append('"').append(k).append('"').append(":
	// ").append("[\n");
	// v.forEach(e ->
	// mapBBuilder.append("\t\t").append('"').append(e).append('"').append(",\n"));
	// mapBBuilder.replace(mapBBuilder.lastIndexOf(","), mapBBuilder.lastIndexOf(",") + 1,
	// "");
	// mapBBuilder.append('\t').append("]\n");
	// if ("]".equals(mapBBuilder.charAt(mapBBuilder.length() - 1))) {
	// mapBBuilder.append(',');
	// }
	// mapBBuilder.append('\n');
	// });
	// mapBBuilder.append("}");
	// return mapBBuilder;
	// }).forEach(builder::append);
	// builder.append("]\n");
	// return builder.toString();
	// }

}
