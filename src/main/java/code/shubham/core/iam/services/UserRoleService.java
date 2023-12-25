package code.shubham.core.iam.services;

import code.shubham.commons.exceptions.InvalidParameterException;
import code.shubham.core.iam.dao.entities.UserRole;
import code.shubham.core.iam.dao.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserRoleService {

	private final UserRoleRepository repository;

	private final RoleService roleService;

	@Autowired
	public UserRoleService(final UserRoleRepository repository, final RoleService roleService) {
		this.repository = repository;
		this.roleService = roleService;
	}

	public UserRole setRoleToUser(final String roleName, final String userId) {
		return this.roleService.getRoleByName(roleName)
			.map(role -> this.repository.save(UserRole.builder().role(role).userId(userId).build()))
			.orElseThrow(() -> new InvalidParameterException("No role with name: %s", roleName));
	}

	public Collection<String> getAllRoles(String userId) {
		return this.repository.findByUserId(userId)
			.stream()
			.map(userRole -> userRole.getRole().getName())
			.collect(Collectors.toSet());
	}

}
