package code.shubham.commons.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BlockController {

	@GetMapping("/internal/block/{seconds}")
	@Hidden
	public void block(@PathVariable final Integer seconds) throws InterruptedException {
		Thread.sleep(seconds * 1000);
		log.info("Blocked for {} seconds", seconds);
	}

}
