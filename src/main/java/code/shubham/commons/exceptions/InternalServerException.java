package code.shubham.commons.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Map;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends RuntimeException {

	private Map<String, List<String>> message;

	public InternalServerException() {
	}

	public InternalServerException(String message) {
		super(message);
	}

	public InternalServerException(Map<String, List<String>> message) {
		this.message = message;
	}

}
