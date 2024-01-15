package code.shubham.core.iam.services;

import code.shubham.core.iam.dao.entities.User;
import code.shubham.core.iam.dao.repositories.UserRepository;
import code.shubham.core.iamcommons.IUserService;
import code.shubham.core.iammodels.GetOrCreateUser;
import code.shubham.core.iammodels.GetUserResponse;
import code.shubham.core.iammodels.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class UserService implements IUserService {

	private final UserRepository repository;

	private final UserRoleService userRoleService;

	@Autowired
	public UserService(final UserRepository repository, final UserRoleService userRoleService) {
		this.repository = repository;
		this.userRoleService = userRoleService;
	}

	@Override
	public GetUserResponse getOrCreate(final GetOrCreateUser.Request request) {
		final User user = this.repository.findByEmail(request.getEmail())
			.orElseGet(() -> this.create(User.builder().name(request.getName()).email(request.getEmail()).build()));
		return GetUserResponse.builder()
			.user(new UserDTO(user.getId(), user.getEmail()))
			.roles(this.userRoleService.getAllRoles(user.getId()))
			.build();
	}

	private User create(final User user) {
		final User persisted = this.repository.save(user);
		this.userRoleService.setRoleToUser("USER", user.getId());
		return persisted;
	}

}
