package code.shubham.commons.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collection;
import java.util.Map;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidParametersException extends InvalidRequestException {

	public InvalidParametersException(final Map<String, Collection<String>> messages) {
		super(messages);
	}

	@Override
	public String getMessage() {
		StringBuilder errorMessageBuilder = new StringBuilder();
		if (this.errorMessages != null)
			for (java.util.Map.Entry<String, Collection<String>> entry : this.errorMessages.entrySet()) {
				String errorMessage = entry.getKey();
				Collection<String> params = entry.getValue();
				errorMessageBuilder.append(String.format(String.join(", ", params), errorMessage));
			}
		return errorMessageBuilder.toString();
	}

}
