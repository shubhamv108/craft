package code.shubham.core.iam.services;

import code.shubham.core.iam.dao.entities.Role;
import code.shubham.core.iam.dao.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class RoleService {

	private final RoleRepository repository;

	private final Map<String, Role> cache = new HashMap<>();

	@Autowired
	public RoleService(RoleRepository repository) {
		this.repository = repository;
	}

	@PostConstruct
	public void init() {
		this.repository.findAnyTen().stream().forEach(role -> this.cache.put(role.getName(), role));
	}

	public Optional<Role> getRoleByName(final String name) {
		return Optional.ofNullable(this.cache.getOrDefault(name, this.repository.findByName(name).orElse(null)));
	}

}
