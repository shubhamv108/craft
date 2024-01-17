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

}
