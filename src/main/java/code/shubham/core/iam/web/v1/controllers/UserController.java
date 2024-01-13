package code.shubham.core.iam.web.v1.controllers;

import code.shubham.commons.utils.ResponseUtils;
import code.shubham.core.iam.services.UserService;
import code.shubham.core.iammodels.GetOrCreateUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(final UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<?> getOrCreateUser(@RequestBody GetOrCreateUser.Request request) {
		return ResponseUtils.getDataResponseEntity(HttpStatus.OK, this.userService.getOrCreate(request));
	}

	@GetMapping("/{userId}")
	public ResponseEntity<?> getById(@PathVariable("userId") final String userId) {
		return ResponseUtils.getDataResponseEntity(HttpStatus.OK, this.userService.getById(userId));
	}

}
