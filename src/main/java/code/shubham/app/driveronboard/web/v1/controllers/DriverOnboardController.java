package code.shubham.app.driveronboard.web.v1.controllers;

import code.shubham.commons.utils.ResponseUtils;
import code.shubham.app.driver.service.DriverService;
import code.shubham.app.drivermodels.CompleteOnboardStageRequest;
import code.shubham.app.driveronboard.web.v1.validators.DriverOnboardStageCompletedRequestValidator;
import code.shubham.app.driveronboard.dao.entities.DriverOnboardStatus;
import code.shubham.app.driveronboard.service.DriverOnboardService;
import code.shubham.app.driveronboardmodels.DriverOnboardDTO;
import code.shubham.app.driveronboardmodels.GetDriverOnboardStatusResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/drivers/onboard")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Driver Onboard")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
public class DriverOnboardController {

	private final DriverOnboardService service;

	private final DriverService driverService;

	@Autowired
	public DriverOnboardController(final DriverOnboardService service, final DriverService driverService) {
		this.service = service;
		this.driverService = driverService;
	}

	@PatchMapping("/updateStatus")
	public ResponseEntity<?> updateStatus(@RequestBody CompleteOnboardStageRequest request) {
		new DriverOnboardStageCompletedRequestValidator().validateOrThrowException(request);
		final DriverOnboardStatus completedStatus = DriverOnboardStatus.valueOf(request.getCompletedOnboardStatus());
		return ResponseUtils.getDataResponseEntity(HttpStatus.OK,
				this.service.updateStatus(completedStatus, request.getDriverOnboardId(), request.getUserId()));
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAll(@RequestParam("driverId") final String driverId,
			@RequestParam("userId") final String userId) {
		return ResponseUtils.getDataResponseEntity(HttpStatus.FOUND,
				GetDriverOnboardStatusResponse.builder()
					.onboards(this.service.fetchAllByDriverIdAndUserId(driverId, userId)
						.stream()
						.map(onboard -> DriverOnboardDTO.builder()
							.driverOnboardId(onboard.getId())
							.driverId(onboard.getDriverId())
							.status(onboard.getStatus().name())
							.userId(onboard.getUserId())
							.build())
						.toList())
					.build());
	}

}
