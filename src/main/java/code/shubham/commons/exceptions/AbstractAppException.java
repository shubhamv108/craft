package code.shubham.commons.exceptions;

public abstract class AbstractAppException extends RuntimeException {

	public AbstractAppException(final String message, String... args) {
		super(String.format(message, args));
	}

}