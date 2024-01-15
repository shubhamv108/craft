package code.shubham.craft.driver.web.v1.controllers;

import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.commons.utils.ResponseUtils;
import code.shubham.commons.utils.Utils;
import code.shubham.craft.driver.cab.services.CabService;
import code.shubham.craft.driver.cabmodels.CabDTO;
import code.shubham.craft.driver.dao.entities.Driver;
import code.shubham.craft.driver.service.DriverService;
import code.shubham.craft.driver.web.v1.validators.MarkAvailableForRideRequestValidator;
import code.shubham.craft.driver.web.v1.validators.RegisterDriverRequestValidator;
import code.shubham.craft.drivermodels.GetDriverDetailsResponse;
import code.shubham.craft.drivermodels.MarkAvailableForRideRequest;
import code.shubham.craft.drivermodels.RegisterDriver;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/drivers")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Driver")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
public class DriverController {

	private final DriverService service;

	private final CabService cabService;

	@Autowired
	public DriverController(final DriverService service, final CabService cabService) {
		this.service = service;
		this.cabService = cabService;
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody final RegisterDriver.Request request) {
		new RegisterDriverRequestValidator().validateOrThrowException(request);

		final Driver driver = Driver.builder()
			.drivingLicenseName(request.getDrivingLicenseName())
			.drivingLicense(request.getDrivingLicense())
			.userId(UserIDContextHolder.get())
			.build();
		final Driver registered = this.service.register(driver, request.getCab());

		return ResponseUtils.getDataResponseEntity(HttpStatus.CREATED,
				RegisterDriver.Response.builder()
					.driverId(registered.getId())
					.drivingLicenseName(registered.getDrivingLicenseName())
					.drivingLicense(registered.getDrivingLicense())
					.userId(registered.getUserId())
					.cab(CabDTO.builder()
						.registrationNumber(request.getCab().getRegistrationNumber())
						.color(request.getCab().getColor())
						.build())
					.build());
	}

	@PatchMapping("/availableForRide")
	public ResponseEntity<?> markAvailableForRide(@RequestBody final MarkAvailableForRideRequest request) {
		new MarkAvailableForRideRequestValidator().validateOrThrowException(request);
		Utils.validateUserOrThrowException(request.getUserId());
		return ResponseUtils.getDataResponseEntity(HttpStatus.OK, this.service.markReadyForRide(request.getDriverId(),
				request.getUserId(), request.getVehicleRegistrationNumber()));
	}

	@GetMapping("/{driverId}")
	public ResponseEntity<?> getById(@RequestParam("userId") final String userId,
			@PathVariable("driverId") final String driverId) {
		Utils.validateUserOrThrowException(userId);
		final Driver driver = this.service.getByIdAndUserId(driverId, userId);
		final List<CabDTO> cabs = this.cabService.getCabsByDriverIdAndUserId(driverId, userId)
			.stream()
			.map(cab -> CabDTO.builder()
				.cabId(cab.getId())
				.registrationNumber(cab.getRegistrationNumber())
				.color(cab.getColor())
				.build())
			.toList();
		return ResponseUtils.getDataResponseEntity(HttpStatus.FOUND,
				GetDriverDetailsResponse.builder()
					.driverId(driver.getId())
					.drivingLicenseName(driver.getDrivingLicenseName())
					.drivingLicense(driver.getDrivingLicense())
					.userId(driver.getUserId())
					.status(driver.getStatus().name())
					.activeCabId(driver.getActiveCabId())
					.cabs(cabs)
					.build());
	}

}
