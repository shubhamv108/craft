package code.shubham.craft.driveronboard.workers.handlers;

import code.shubham.commons.handlers.EventHandler;
import code.shubham.commons.models.Event;
import code.shubham.commons.utils.JsonUtils;
import code.shubham.craft.driveronboard.dao.entities.DriverOnboardStatus;
import code.shubham.craft.driveronboard.service.DriverOnboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Scope(scopeName = "prototype")
@Component("BackgroundVerificationStatusUpdatedEventHandler")
public class BackgroundVerificationStatusUpdatedEventHandler implements EventHandler {

	private final DriverOnboardService service;

	@Autowired
	public BackgroundVerificationStatusUpdatedEventHandler(final DriverOnboardService service) {
		this.service = service;
	}

	@Override
	public Object handle(final Event event) {
		log.debug(String.format("Event Data: %s", event.getData()));
		final Map<String, Object> data = JsonUtils.as(event.getData(), HashMap.class);
		if (!"COMPLETED".equals(data.get("status")) || !"DRIVER".equals(data.get("applicantType"))) {
			log.info("[SKIP] Event skipped: {}", event);
			return null;
		}
		return this.service.markCompletedStatusByUniqueReferenceId(DriverOnboardStatus.BACKGROUND_VERIFICATION,
				event.getUniqueReferenceId(), (String) data.get("userId"));
	}

}
