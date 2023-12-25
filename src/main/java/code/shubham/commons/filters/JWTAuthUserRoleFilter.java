package code.shubham.commons.filters;

import code.shubham.commons.contexts.RoleContextHolder;
import code.shubham.commons.contexts.UserContextHolder;
import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.core.iamcommons.IUserService;
import code.shubham.core.iammodels.GetOrCreateUser;
import code.shubham.core.iammodels.GetUserResponse;
import code.shubham.core.iammodels.UserDTO;
import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Slf4j
@Component
@Order(8)
public class JWTAuthUserRoleFilter implements Filter {

	private final IUserService userService;

	@Autowired
	public JWTAuthUserRoleFilter(final IUserService userService) {
		this.userService = userService;
	}

	@Override
	public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
			final FilterChain chain) throws java.io.IOException, ServletException {
		try {
			UserDTO user = null;
			if (UserIDContextHolder.get() == null)
				user = this.getUserFromSecurityContext();

			if (user != null) {
				UserContextHolder.set(user);
				UserIDContextHolder.set(user.id());
			}

			log.info(String.format("Requesting user: %s", UserIDContextHolder.get()));

			chain.doFilter(servletRequest, servletResponse);
		}
		finally {
			UserIDContextHolder.clear();
			UserContextHolder.clear();
			RoleContextHolder.clear();
		}
	}

	private UserDTO getUserFromSecurityContext() {
		if (!(SecurityContextHolder.getContext().getAuthentication() instanceof JwtAuthenticationToken))
			return null;
		return this.currentUser((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	}

	private UserDTO currentUser(final Jwt jwt) {
		final String email = jwt.getClaims().get("email").toString();
		final String name = jwt.getClaims().get("name").toString();
		final GetUserResponse response = this.userService
			.getOrCreate(GetOrCreateUser.Request.builder().email(email).name(name).build());
		RoleContextHolder.set(new HashSet<>(response.getRoles()));
		return response.getUser();
	}

}
