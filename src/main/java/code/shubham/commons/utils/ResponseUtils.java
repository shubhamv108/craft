package code.shubham.commons.utils;

import code.shubham.commons.models.ServiceResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public class ResponseUtils {

	public static ResponseEntity<?> getErrorResponseEntity(final int statusCode, final Object errors) {
		return getResponseEntity(statusCode, null, errors);
	}

	public static ResponseEntity<?> getDataResponseEntity(final HttpStatus status, final Object data) {
		return getResponseEntity(status.value(), data, null);
	}

	public static ResponseEntity<?> getDataResponseEntity(final int statusCode, final Object data) {
		return getResponseEntity(statusCode, data, null);
	}

	public static ResponseEntity<?> getResponseEntity(final int statusCode, final Object data, final Object errors) {
		ServiceResponse response = ServiceResponse.builder().statusCode(statusCode).data(data).error(errors).build();
		return ResponseEntity.status(statusCode).body(response);
	}

	public static ResponseEntity<?> getResponseEntity(final int statusCode, final Object data,
			final Map<String, List<String>> headers) {
		return getResponseEntity(statusCode, data, null, headers);
	}

	public static ResponseEntity<?> getResponseEntity(final int statusCode, final Object data, final Object errors,
			final Map<String, List<String>> headers) {
		final ServiceResponse response = ServiceResponse.builder()
			.statusCode(statusCode)
			.data(data)
			.error(errors)
			.build();
		HttpHeaders httpHeaders = null;
		if (headers != null) {
			httpHeaders = new HttpHeaders();
			headers.forEach(httpHeaders::addAll);
		}
		return ResponseEntity.status(statusCode).headers(httpHeaders).body(response);
	}

}
