package code.shubham.craft.driver.web.v1.controllers;

import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.commons.utils.ResponseUtils;
import code.shubham.commons.utils.Utils;
import code.shubham.craft.driver.dao.entities.Driver;
import code.shubham.craft.driver.service.DriverService;
import code.shubham.craft.driver.web.v1.validators.MarkAvailableForRideRequestValidator;
import code.shubham.craft.driver.web.v1.validators.RegisterDriverRequestValidator;
import code.shubham.craft.drivermodels.MarkAvailableForRideRequest;
import code.shubham.craft.drivermodels.RegisterDriverRequest;
import code.shubham.craft.drivermodels.RegisterDriverResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drivers/v1")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Driver")
public class DriverController {

	private final DriverService service;

	@Autowired
	public DriverController(final DriverService service) {
		this.service = service;
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody final RegisterDriverRequest request) {
		new RegisterDriverRequestValidator().validateOrThrowException(request);
		final Driver driver = this.service.register(Driver.builder()
			.vehicleColor(request.getVehicleColor())
			.vehicleRegistrationNumber(request.getVehicleRegistrationNumber())
			.userId(UserIDContextHolder.get())
			.drivingLicense(request.getDrivingLicense())
			.build());
		return ResponseUtils.getDataResponseEntity(HttpStatus.CREATED,
				RegisterDriverResponse.builder()
					.driverId(driver.getId())
					.vehicleRegistrationNumber(driver.getVehicleRegistrationNumber())
					.vehicleColor(driver.getVehicleColor())
					.drivingLicense(driver.getDrivingLicense())
					.userId(driver.getUserId())
					.build());
	}

	@PatchMapping("/availableForRide")
	public ResponseEntity<?> markAvailableForRide(@RequestBody MarkAvailableForRideRequest request) {
		new MarkAvailableForRideRequestValidator().validate(request);
		Utils.validateUserOrThrowException(request.getUserId());
		return ResponseUtils.getDataResponseEntity(HttpStatus.OK,
				this.service.markReadyForRide(request.getDriverId(), request.getUserId()));
	}

	@GetMapping("/{driverId}")
	public ResponseEntity<?> getById(@RequestParam("userId") final String userId,
			@PathVariable("driverId") final String driverId) {
		Utils.validateUserOrThrowException(userId);
		return ResponseUtils.getDataResponseEntity(HttpStatus.FOUND, this.service.getByIdAndUserId(driverId, userId));
	}

}
