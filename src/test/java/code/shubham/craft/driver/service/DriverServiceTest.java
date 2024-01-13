package code.shubham.craft.driver.service;

import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.contexts.UserContextHolder;
import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.core.iammodels.UserDTO;
import code.shubham.craft.driver.cabmodels.CabDTO;
import code.shubham.craft.driver.dao.entities.Driver;
import code.shubham.craft.driver.dao.entities.DriverStatus;
import code.shubham.craft.driver.dao.repositories.DriverRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DriverServiceTest {

	@Mock
	private DriverRepository repository;

	@InjectMocks
	private DriverService service;

	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void register_with_existing_driver_for_userId() {
		UserContextHolder.set(new UserDTO(TestCommonConstants.USER_ID, "test@test"));
		final Driver driver = Driver.builder()
			.userId(TestCommonConstants.USER_ID)
			.status(DriverStatus.ACTIVE)
			.drivingLicenseName("")
			.build();
		when(this.repository.findByUserId(TestCommonConstants.USER_ID)).thenReturn(Optional.of(driver));

		InvalidRequestException exception = Assertions.assertThrows(InvalidRequestException.class,
				() -> this.service.register(driver, CabDTO.builder().build()));
		Assertions.assertTrue(exception.getOriginalErrors().stream().findFirst().get().containsKey("email"));
		Assertions.assertEquals(
				((ArrayList<?>) exception.getOriginalErrors().stream().findFirst().get().get("email")).get(0),
				"User with email: test@test already registered as a driver");
	}

}