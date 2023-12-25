package code.shubham.craft.order.web.v1.controllers;

import code.shubham.commons.contexts.RoleContextHolder;
import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.commons.utils.ResponseUtils;
import code.shubham.commons.utils.Utils;
import code.shubham.craft.order.services.OrderService;
import code.shubham.craft.ordermodels.GetAllOrdersResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders/v1")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Order")
public class OrderController {

	private final OrderService service;

	@Autowired
	public OrderController(final OrderService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<?> getAllOrders(@RequestParam("userId") final String userId) {
		Utils.validateUserOrThrowException(userId);
		return ResponseUtils.getDataResponseEntity(HttpStatus.FOUND,
				GetAllOrdersResponse.builder()
					.orders(this.service.fetchAllByUserId(userId)
						.stream()
						.map(order -> GetAllOrdersResponse.Order.builder()
							.orderId(order.getId())
							.status(order.getStatus().name())
							.userId(order.getUserId())
							.build())
						.toList())
					.build());
	}

}
