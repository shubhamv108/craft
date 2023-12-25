package code.shubham.commons.filters;

import code.shubham.commons.contexts.TenantContextHolder;
import code.shubham.commons.contexts.UserContextHolder;
import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.core.iam.dao.entities.User;
import code.shubham.core.iam.services.UserService;
import code.shubham.core.iammodels.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

@Slf4j
@Component
@Order(7)
public class ContextFilter implements Filter {

	private final UserService userService;

	@Autowired
	public ContextFilter(final UserService userService) {
		this.userService = userService;
	}

	@Override
	public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
			final FilterChain chain) throws java.io.IOException, ServletException {
		try {
			final HttpServletRequest request = (HttpServletRequest) servletRequest;
			final String userId = Optional.ofNullable(request.getHeader("userId"))
				.orElse((String) request.getAttribute("userId"));
			if (userId != null)
				UserIDContextHolder.set(userId);

			final String tenantId = request.getHeader("tenantId");
			if (tenantId != null)
				TenantContextHolder.setTenant(tenantId);

			final String userEmail = Optional.ofNullable(request.getHeader("userEmail"))
				.orElse((String) request.getAttribute("userEmail"));
			if (userEmail != null)
				UserContextHolder.set(new UserDTO(userId, userEmail));

			chain.doFilter(servletRequest, servletResponse);
		}
		finally {
			UserContextHolder.clear();
			UserIDContextHolder.clear();
			TenantContextHolder.clear();
		}
	}

}
