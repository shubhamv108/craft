package code.shubham.commons.utils;

import code.shubham.commons.Constants;
import code.shubham.commons.models.Event;
import code.shubham.commons.models.Label;
import code.shubham.commons.models.MetricEvent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MetricsLogger {

	@Value("${service.name}")
	private String serviceName;

	@Value("${service.module}")
	private String serviceModule;

	public void log(final HttpServletRequest request) {
		final Long requestStartTimestamp = (Long) request.getAttribute(Constants.RequestKey.REQUEST_START_TIMESTAMP);
		log.info(MetricEvent.builder()
			.name("api.execution.duration")
			.label(Label.builder()
				.key("service")
				.value(this.serviceName)
				.add()
				.key("module")
				.value(this.serviceModule)
				.add()
				.key("api")
				.value(request.getRequestURI())
				.build())
			.timestamp(System.currentTimeMillis())
			.value(System.currentTimeMillis() - requestStartTimestamp)
			.build()
			.toString());
	}

	public void log(final Event event, final Long requestStartTimestamp, final Class handler) {
		if (event == null)
			return;

		log.info(MetricEvent.builder()
			.name("event.handler.execution.duration")
			.label(Label.builder()
				.key("service")
				.value(this.serviceName)
				.add()
				.key("module")
				.value(this.serviceModule)
				.add()
				.key("handler")
				.value(handler.getSimpleName())
				.add()
				.key("type")
				.value(event.getEventType())
				.add()
				.key("name")
				.value(event.getEventName())
				.build())
			.timestamp(System.currentTimeMillis())
			.value(System.currentTimeMillis() - requestStartTimestamp)
			.build()
			.toString());
	}

}
