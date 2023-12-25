package code.shubham.commons.filters;

import code.shubham.commons.Constants;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@Order(2)
public class RequestTimestampFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws java.io.IOException, ServletException {
		final Long now = System.currentTimeMillis();

		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		request.setAttribute(Constants.RequestKey.REQUEST_START_TIMESTAMP, now);
		Object requestId = request.getAttribute(Constants.RequestKey.REQUEST_ID);
		if (requestId == null) {
			requestId = UUID.randomUUID().toString();
			request.setAttribute(Constants.RequestKey.REQUEST_ID, requestId);
		}
		ThreadContext.put(Constants.RequestKey.REQUEST_ID, (String) requestId);
		chain.doFilter(servletRequest, servletResponse);
		ThreadContext.clearAll();
	}

}
