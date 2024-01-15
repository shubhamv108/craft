package code.shubham.craft.driveronboard.service;

import code.shubham.commons.AbstractSpringBootTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class DriverOnboardServiceTest extends AbstractSpringBootTest {

	@Autowired
	private DriverOnboardService service;

	@BeforeEach
	protected void setUp() {
		super.setUp();
		truncate("driver_onboard");
	}

	@AfterEach
	void tearDown() {
		truncate("driver_onboard");
	}

}