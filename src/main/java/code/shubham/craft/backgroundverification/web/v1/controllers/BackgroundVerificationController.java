package code.shubham.craft.backgroundverification.web.v1.controllers;

import code.shubham.commons.annotations.Role;
import code.shubham.commons.utils.ResponseUtils;
import code.shubham.commons.utils.Utils;
import code.shubham.craft.backgroundverification.dao.entities.BackgroundVerificationStatus;
import code.shubham.craft.backgroundverification.services.BackgroundVerificationService;
import code.shubham.craft.backgroundverification.web.v1.validators.UpdateBackgroundVerificationStatusRequestValidator;
import code.shubham.craft.backgroundverificatonmodels.UpdateBackgroundVerificationStatusRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/backgroundVerification")
@Role("ADMIN")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Background Verification")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
public class BackgroundVerificationController {

	private final BackgroundVerificationService service;

	@Autowired
	public BackgroundVerificationController(final BackgroundVerificationService service) {
		this.service = service;
	}

	@PatchMapping("/updateStatus")
	public ResponseEntity<?> updateStatus(@RequestBody final UpdateBackgroundVerificationStatusRequest request) {
		new UpdateBackgroundVerificationStatusRequestValidator().validateOrThrowException(request);
		return ResponseUtils.getDataResponseEntity(HttpStatus.OK, this.service.updateStatus(
				request.getClientReferenceId(), BackgroundVerificationStatus.valueOf(request.getCompletedStatus())));
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAll(@RequestParam final String userId) {
		Utils.validateUserOrThrowException(userId);
		return ResponseUtils.getDataResponseEntity(HttpStatus.OK, this.service.fetchAllByUserId(userId));
	}

}
