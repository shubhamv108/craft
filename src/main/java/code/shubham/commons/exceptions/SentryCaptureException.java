package code.shubham.commons.exceptions;

import code.shubham.commons.utils.JsonUtils;
import lombok.Builder;

public class SentryCaptureException extends Exception {

	@Builder
	private static class Data {

		private String data;

	}

	public SentryCaptureException(final Exception exception, final String data) {
		super(JsonUtils.get(Data.builder().data(data).build()), exception);
	}

}
