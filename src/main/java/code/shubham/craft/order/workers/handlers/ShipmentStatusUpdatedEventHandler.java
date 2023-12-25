package code.shubham.craft.order.workers.handlers;

import code.shubham.commons.handlers.EventHandler;
import code.shubham.commons.models.Event;
import code.shubham.commons.utils.JsonUtils;
import code.shubham.craft.order.dao.entities.OrderProduct;
import code.shubham.craft.order.dao.entities.OrderProductStatus;
import code.shubham.craft.order.dao.entities.OrderStatus;
import code.shubham.craft.order.services.OrderProductService;
import code.shubham.craft.order.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Scope("prototype")
@Component("ShipmentStatusUpdatedEventHandler")
public class ShipmentStatusUpdatedEventHandler implements EventHandler {

	private final OrderService service;

	private final OrderProductService orderProductService;

	@Autowired
	public ShipmentStatusUpdatedEventHandler(final OrderService service,
			final OrderProductService orderProductService) {
		this.service = service;
		this.orderProductService = orderProductService;
	}

	@Override
	public Object handle(final Event event) {
		log.debug(String.format("Event Data: %s", event.getData()));
		final Map<String, Object> data = JsonUtils.as(event.getData(), HashMap.class);
		final OrderProduct orderProduct = this
			.getCompletedOrderProductStatusFromShipmentStatus((String) data.get("status"))
			.map(orderProductStatus -> this.orderProductService.updateStatus(orderProductStatus,
					event.getUniqueReferenceId()))
			.orElseGet(() -> {
				log.info("[SKIP] Event skipped");
				return null;
			});

		if (OrderProductStatus.COMPLETED.name().equals(orderProduct.getStatus().name()))
			return this.service.updateStatus((String) data.get("orderId"), OrderStatus.CREATED);

		return orderProduct;
	}

	private Optional<OrderProductStatus> getCompletedOrderProductStatusFromShipmentStatus(final String shipmentStatus) {
		switch (shipmentStatus) {
			case "PREPARE_TO_DISPATCH":
				return Optional.ofNullable(OrderProductStatus.CREATED);
			case "DELIVERED":
				return Optional.ofNullable(OrderProductStatus.SHIPPED);
		}
		return Optional.empty();
	}

}
