package code.shubham.core.iam.services;

import code.shubham.test.AbstractSpringBootTest;
import code.shubham.commons.TestCommonConstants;
import code.shubham.core.iam.dao.repositories.UserRepository;
import code.shubham.core.iam.dao.repositories.UserRoleRepository;
import code.shubham.core.iammodels.GetOrCreateUser;
import code.shubham.core.iammodels.GetUserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UserServiceTest extends AbstractSpringBootTest {

	@Autowired
	private UserService service;

	@Autowired
	private UserRepository repository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Test
	void getOrCreate() {
		final GetUserResponse response = this.service.getOrCreate(
				GetOrCreateUser.Request.builder().name("Shubham").email(TestCommonConstants.USER_EMAIL).build());

		final var user = this.repository.findByEmail(TestCommonConstants.USER_EMAIL);
		final var roles = this.userRoleRepository.findByUserId(user.get().getId());
		Assertions.assertEquals(response.getUser().email(), TestCommonConstants.USER_EMAIL);
		Assertions.assertNotNull(response.getUser().id());
		Assertions.assertEquals(response.getRoles().stream().findFirst().get(), "USER");
		Assertions.assertTrue(user.isPresent());
		Assertions.assertEquals("Shubham", user.get().getName());
		Assertions.assertEquals(1, roles.size());
		Assertions.assertEquals("USER", roles.get(0).getRole().getName());
	}

	@Test
	void getById() {
	}

}