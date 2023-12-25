package code.shubham.commons.dao.entities;

import code.shubham.commons.contexts.UserIDContextHolder;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class UserIdAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		final Optional<String> userId = Optional.ofNullable(UserIDContextHolder.get());
		if (userId.isPresent())
			return userId;

		return Optional.ofNullable("$$NONE$$");
	}

}