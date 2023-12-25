package code.shubham.commons.utils;

import code.shubham.commons.models.ServiceResponse;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {

	public static ResponseEntity<?> getOK() {
		return ResponseEntity.status(org.springframework.http.HttpStatus.OK).build();
	}

	public static ResponseEntity<?> getErrorResponseEntity(int statusCode, Object errors) {
		return getResponseEntity(statusCode, null, errors);
	}

	public static ResponseEntity<?> getResponseEntity(org.springframework.http.HttpStatus status) {
		return getResponseEntity(status.value(), null, null);
	}

	public static ResponseEntity<?> getResponseEntity(int statusCode) {
		return getResponseEntity(statusCode, null, null);
	}

	public static ResponseEntity<?> getDataResponseEntity(org.springframework.http.HttpStatus status, Object data) {
		return getResponseEntity(status.value(), data, null);
	}

	public static ResponseEntity<?> getDataResponseEntity(int statusCode, Object data) {
		return getResponseEntity(statusCode, data, null);
	}

	public static ResponseEntity<?> getResponseEntity(int statusCode, Object data, Object errors) {
		ServiceResponse response = ServiceResponse.builder().statusCode(statusCode).data(data).error(errors).build();
		return ResponseEntity.status(statusCode).body(response);
	}

	public static ResponseEntity<?> getResponseEntity(int statusCode, Object data, Object errors, String headerName,
			String... headerValues) {
		ServiceResponse response = ServiceResponse.builder().statusCode(statusCode).data(data).error(errors).build();
		return ResponseEntity.status(statusCode).header(headerName, headerValues).body(response);
	}

	public static ResponseEntity<?> getResponseEntity(int statusCode, Object data,
			java.util.Map<String, java.util.List<String>> headers) {
		return getResponseEntity(statusCode, data, null, headers);
	}

	public static ResponseEntity<?> getResponseEntity(int statusCode, Object data,
			java.util.Map<String, java.util.List<String>> headers, String headerKey, String... headerValues) {
		headers.put(headerKey, java.util.Arrays.asList(headerValues));
		return getResponseEntity(statusCode, data, null, headers);
	}

	public static ResponseEntity<?> getResponseEntity(int statusCode, Object data, Object errors,
			java.util.Map<String, java.util.List<String>> headers) {
		ServiceResponse response = ServiceResponse.builder().statusCode(statusCode).data(data).error(errors).build();
		org.springframework.http.HttpHeaders httpHeaders = null;
		if (headers != null) {
			httpHeaders = new org.springframework.http.HttpHeaders();
			headers.forEach(httpHeaders::addAll);
		}
		return ResponseEntity.status(statusCode).headers(httpHeaders).body(response);
	}

}
