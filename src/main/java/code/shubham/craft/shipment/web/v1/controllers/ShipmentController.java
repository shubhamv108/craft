package code.shubham.craft.shipment.web.v1.controllers;

import code.shubham.commons.annotations.Role;
import code.shubham.commons.utils.ResponseUtils;
import code.shubham.craft.shipment.dao.entities.ShipmentStatus;
import code.shubham.craft.shipment.services.ShipmentService;
import code.shubham.craft.shipment.web.v1.validators.UpdateShipmentStatusRequestValidator;
import code.shubham.craft.shipmentmodels.UpdateShipmentStatusRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipments/v1")
@Role("ADMIN")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Shipment")
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
