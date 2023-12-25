package code.shubham.craft.shipment.workers.handlers;

import code.shubham.commons.handlers.EventHandler;
import code.shubham.commons.models.Event;
import code.shubham.commons.utils.JsonUtils;
import code.shubham.craft.order.dao.entities.OrderStatus;
import code.shubham.craft.ordermodels.OrderEventData;
import code.shubham.craft.shipment.services.ShipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Scope(scopeName = "prototype")
@Component("OrderCreatedEventHandler")
public class OrderCreatedEventHandler implements EventHandler {

	private final ShipmentService service;

	@Autowired
	public OrderCreatedEventHandler(final ShipmentService service) {
		this.service = service;
	}

	@Override
	public Object handle(final Event event) {
		log.info(String.format("Event Data: %s", event.getData()));
		final OrderEventData data = JsonUtils.as(event.getData(), OrderEventData.class);
		if (!"CREATED".equals(data.getOrder().getStatus())) {
			log.info("[SKIP] Event skipped: %s", event);
			return null;
		}
		return this.service.create(data.getOrder().getOrderId(), data.getProducts(), null, null, event.getUserId());
	}

}
