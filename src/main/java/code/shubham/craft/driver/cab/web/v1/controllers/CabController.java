package code.shubham.craft.driver.cab.web.v1.controllers;

import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.commons.utils.ResponseUtils;
import code.shubham.commons.utils.Utils;
import code.shubham.craft.driver.cabmodels.RegisterCabRequest;
import code.shubham.craft.driver.cab.services.CabService;
import code.shubham.craft.driver.cab.web.v1.validation.RegisterCabRequestValidator;
import code.shubham.craft.driver.service.DriverService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cabs/v1")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Cab")
public class CabController {

	private final CabService service;

	private final DriverService driverService;

	@Autowired
	public CabController(final CabService service, final DriverService driverService) {
		this.service = service;
		this.driverService = driverService;
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody final RegisterCabRequest request) {
		new RegisterCabRequestValidator().validateOrThrowException(request);
		this.validateDriver(request.getDriverId(), request.getUserId());
		return ResponseUtils.getDataResponseEntity(HttpStatus.CREATED, this.service.add(request));
	}

	@GetMapping
	public ResponseEntity<?> getAll(@RequestParam("driverId") final String driverId,
			@RequestParam("userId") final String userId) {
		this.validateDriver(driverId, userId);
		return ResponseUtils.getDataResponseEntity(HttpStatus.FOUND,
				this.service.getCabsByDriverIdAndUserId(driverId, userId));
	}

	private void validateDriver(final String driverId, final String userId) {
		Utils.validateUserOrThrowException(userId);
		this.driverService.fetchByIdAndUserId(driverId, userId)
			.orElseThrow(() -> new InvalidRequestException("driverId", "no driver found for driverId: %s", driverId));
	}

}
