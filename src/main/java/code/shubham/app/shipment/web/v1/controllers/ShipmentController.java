package code.shubham.app.shipment.web.v1.controllers;

import code.shubham.commons.annotations.Role;
import code.shubham.commons.utils.ResponseUtils;
import code.shubham.app.shipment.dao.entities.ShipmentStatus;
import code.shubham.app.shipment.services.ShipmentService;
import code.shubham.app.shipment.web.v1.validators.UpdateShipmentStatusRequestValidator;
import code.shubham.app.shipmentmodels.UpdateShipmentStatusRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/shipments")
@Role("ADMIN")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Shipment")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
public class ShipmentController {

	private final ShipmentService service;

	@Autowired
	public ShipmentController(final ShipmentService service) {
		this.service = service;
	}

	@PatchMapping("/updateStatus")
	public ResponseEntity<?> updateStatus(@RequestBody final UpdateShipmentStatusRequest request) {
		new UpdateShipmentStatusRequestValidator().validateOrThrowException(request);
		return ResponseUtils.getDataResponseEntity(HttpStatus.OK, this.service
			.updateStatus(request.getUniqueReferenceId(), ShipmentStatus.valueOf(request.getCompletedStatus())));
	}

}
