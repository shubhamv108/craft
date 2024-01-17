package code.shubham.app.driveronboard.workers.handlers;

import code.shubham.commons.handlers.EventHandler;
import code.shubham.commons.models.Event;
import code.shubham.commons.utils.JsonUtils;
import code.shubham.app.driveronboard.service.DriverOnboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Scope(value = "prototype")
@Component("DriverStatusUpdatedEventHandler")
public class DriverStatusUpdatedEventHandler implements EventHandler {

	private final DriverOnboardService service;

	@Autowired
	public DriverStatusUpdatedEventHandler(final DriverOnboardService service) {
		this.service = service;
	}

	@Override
	public Object handle(final Event event) {
		final Map<String, Object> data = JsonUtils.as(event.getData(), HashMap.class);
		if (!"ONBOARDING".equals((String) data.get("status"))) {
			log.info("[SKIP] Processing event: {}", event);
			return null;
		}
		return this.service.create((String) data.get("id"), event.getUserId(), event.getUniqueReferenceId());
	}

}
