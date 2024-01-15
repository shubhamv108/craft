package code.shubham.craft.order.web.v1.controllers;

import code.shubham.commons.annotations.Role;
import code.shubham.commons.contexts.RoleContextHolder;
import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.commons.utils.ResponseUtils;
import code.shubham.commons.utils.Utils;
import code.shubham.craft.order.dao.entities.Order;
import code.shubham.craft.order.services.OrderProductService;
import code.shubham.craft.order.services.OrderService;
import code.shubham.craft.ordermodels.GetAllOrdersResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/orders/{orderId}/products")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Order Product")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
public class OrderProductController {

	private final OrderService orderService;

	private final OrderProductService service;

	@Autowired
	public OrderProductController(final OrderService orderService, final OrderProductService service) {
		this.orderService = orderService;
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<?> getAllOrderProducts(@PathVariable("orderId") final String orderId,
			@RequestParam("userId") final String userId) {
		Utils.validateUserOrThrowException(userId);
		this.orderService.fetchByUserIdAndOrderOrderId(userId, orderId)
			.orElseThrow(() -> new InvalidRequestException("orderId", "No order found for oderId: %s", orderId));
		return ResponseUtils.getDataResponseEntity(HttpStatus.FOUND, this.service.fetchAllByOrderId(orderId));
	}

}
