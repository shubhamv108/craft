package code.shubham.craft.order.workers.handlers;

import code.shubham.commons.handlers.EventHandler;
import code.shubham.commons.models.Event;
import code.shubham.commons.utils.JsonUtils;
import code.shubham.craft.order.services.OrderService;
import code.shubham.craft.ordermodels.CreateOrderCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Scope("prototype")
@Component("CreateOrderCommandEventHandler")
public class CreateOrderCommandEventHandler implements EventHandler {

	private final OrderService service;

	@Autowired
	public CreateOrderCommandEventHandler(final OrderService service) {
		this.service = service;
	}

	@Override
	public Object handle(final Event event) {
		final CreateOrderCommand command = JsonUtils.as(event.getData(), CreateOrderCommand.class);
		return this.service.create(command);
	}

}
