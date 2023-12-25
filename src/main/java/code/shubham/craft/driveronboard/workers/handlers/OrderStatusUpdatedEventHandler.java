package code.shubham.craft.driveronboard.workers.handlers;

import code.shubham.commons.handlers.EventHandler;
import code.shubham.commons.models.Event;
import code.shubham.commons.utils.JsonUtils;
import code.shubham.craft.driveronboard.dao.entities.DriverOnboardStatus;
import code.shubham.craft.driveronboard.service.DriverOnboardService;
import code.shubham.craft.ordermodels.OrderEventData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Scope("prototype")
@Component("OrderStatusUpdatedEventHandler")
public class OrderStatusUpdatedEventHandler implements EventHandler {

	private final DriverOnboardService service;

	@Autowired
	public OrderStatusUpdatedEventHandler(final DriverOnboardService service) {
		this.service = service;
	}

	@Override
	public Object handle(final Event event) {
		final OrderEventData data = JsonUtils.as(event.getData(), OrderEventData.class);
		if (!"COMPLETED".equals((String) data.getOrder().getStatus())) {
			log.info("[SKIP] Event skipped: {}", event);
			return null;
		}
		return this.service.markCompletedStatusByUniqueReferenceId(DriverOnboardStatus.SHIPPING_OF_TRACKING_DEVICE,
				event.getUniqueReferenceId(), event.getUserId());
	}

}
