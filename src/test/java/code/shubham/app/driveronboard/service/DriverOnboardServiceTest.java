package code.shubham.app.driveronboard.service;

import code.shubham.test.AbstractSpringBootTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

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