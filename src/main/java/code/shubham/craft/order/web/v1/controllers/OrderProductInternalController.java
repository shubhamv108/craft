package code.shubham.craft.order.web.v1.controllers;

import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.commons.utils.ResponseUtils;
import code.shubham.craft.order.services.OrderProductService;
import code.shubham.craft.order.services.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/orders/v1/{orderId}/products")
@Tag(name = "Order Product")
public class OrderProductInternalController {

	private final OrderService orderService;

	private final OrderProductService service;

	@Autowired
	public OrderProductInternalController(final OrderService orderService, final OrderProductService service) {
		this.orderService = orderService;
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<?> getAllOrderProducts(@PathVariable("orderId") final String orderId) {
		this.orderService.fetchByOrderId(orderId)
			.orElseThrow(() -> new InvalidRequestException("orderId", "No order found for oderId: %s", orderId));
		return ResponseUtils.getDataResponseEntity(HttpStatus.FOUND, this.service.fetchAllByOrderId(orderId));
	}

}
