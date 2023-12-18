package code.shubham.commons.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class SecureController {

	@GetMapping(path = "/")
	public String index() {
		return "external";
	}

	@GetMapping(path = "/secure")
	public String secure(Principal principal, Model model) {
		model.addAttribute("test-model", null);
		return "test-model";
	}

}
