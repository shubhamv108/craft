package code.shubham.commons.exceptions;

import code.shubham.commons.utils.ResponseUtils;
import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ AccessDeniedException.class, UnauthorizedException.class })
	public ResponseEntity<?> handleAccessDeniedException(final Exception exception, final WebRequest request) {
		log.error("", exception);
		return ResponseUtils.getErrorResponseEntity(HttpStatus.FORBIDDEN.value(), "Access denied");
	}

	@ExceptionHandler({ SQLIntegrityConstraintViolationException.class })
	public ResponseEntity<?> handleSQLIntegrityConstraintViolationException(
			final SQLIntegrityConstraintViolationException exception, final WebRequest request) {
		return ResponseUtils.getErrorResponseEntity(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
	}

	@ExceptionHandler({ InvalidRequestException.class })
	public ResponseEntity<?> handleInvalidRequestException(final InvalidRequestException exception,
			final WebRequest request) {
		log.error(exception.getMessage(), exception);
		return ResponseUtils.getErrorResponseEntity(HttpStatus.BAD_REQUEST.value(), exception.getOriginalErrors());
	}

	@ExceptionHandler({ ServiceInvocationClientException.class })
	public ResponseEntity<?> handleServiceInvocationClientException(final InvalidRequestException exception,
			final WebRequest request) {
		log.error(exception.getMessage(), exception);
		Sentry.captureException(exception);
		return ResponseUtils.getErrorResponseEntity(HttpStatus.SERVICE_UNAVAILABLE.value(),
				exception.getOriginalErrors());
	}

	@ExceptionHandler({ BlobStoreException.class })
	public ResponseEntity<?> handleBlobStoreException(final InvalidRequestException exception,
			final WebRequest request) {
		log.error(exception.getMessage(), exception);
		Sentry.captureException(exception);
		return ResponseUtils.getErrorResponseEntity(HttpStatus.BAD_REQUEST.value(), exception.getOriginalErrors());
	}

	@ExceptionHandler({ PropertyValueException.class, DataIntegrityViolationException.class, Exception.class })
	public ResponseEntity<?> handleServerExceptions(final Exception exception, final WebRequest request) {
		log.error("", exception);
		Sentry.captureException(exception);
		return ResponseUtils.getErrorResponseEntity(HttpStatus.SERVICE_UNAVAILABLE.value(), "Something went wrong");
	}

	@ExceptionHandler({ InternalServerException.class })
	public ResponseEntity<?> handleInternalServerException(final InternalServerException exception,
			final WebRequest request) {
		log.error("", exception);
		Sentry.captureException(exception);
		return ResponseUtils.getErrorResponseEntity(HttpStatus.SERVICE_UNAVAILABLE.value(), exception.getMessage());
	}

}