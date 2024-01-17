package code.shubham.app.backgroundverification.workers.handlers;

import code.shubham.commons.handlers.EventHandler;
import code.shubham.commons.models.Event;
import code.shubham.commons.utils.JsonUtils;
import code.shubham.app.backgroundverification.services.BackgroundVerificationService;
import code.shubham.app.driveronboard.dao.entities.DriverOnboardStatus;
import code.shubham.app.driveronboardmodels.DriverOnboardStatusUpdatedEventData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Scope(scopeName = "prototype")
@Component("DriverOnboardStatusUpdatedEventHandler")
public class DriverOnboardStatusUpdatedEventHandler implements EventHandler {

	private final BackgroundVerificationService service;

	@Autowired
	public DriverOnboardStatusUpdatedEventHandler(final BackgroundVerificationService service) {
		this.service = service;
	}

	@Override
	public Object handle(final Event event) {
		final DriverOnboardStatusUpdatedEventData data = JsonUtils.as(event.getData(),
				DriverOnboardStatusUpdatedEventData.class);
		if (!DriverOnboardStatus.BACKGROUND_VERIFICATION.name().equals(data.getDriverOnboard().getStatus())) {
			log.info("[SKIP] Event skipped: {}", event);
			return null;
		}
		return this.service.getOrCreate(data.getDriverOnboard().getDriverId(), "DRIVER", event.getUserId(),
				data.getBackgroundVerificationId());
	}

}
