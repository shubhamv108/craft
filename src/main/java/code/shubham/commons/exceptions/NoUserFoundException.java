package code.shubham.commons.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NoUserFoundException extends Exception {

	public NoUserFoundException(final String userId) {
		super("No user found with id: " + userId);
	}

}
