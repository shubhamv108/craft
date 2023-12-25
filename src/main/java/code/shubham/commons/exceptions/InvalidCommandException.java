package code.shubham.commons.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCommandException extends ClientException {

	public InvalidCommandException(final Object command, final String... message) {
		super(String.format("Invalid command %s, %s", command, message));
	}

}
