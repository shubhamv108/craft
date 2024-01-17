package code.shubham.commons.filters;

import code.shubham.commons.Constants;
import code.shubham.commons.models.Label;
import code.shubham.commons.models.MetricEvent;
import code.shubham.commons.utils.MetricsLogger;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Slf4j
@Component
@Order(3)
public class RequestResponseLoggingFilter implements Filter {

	@Autowired
	private MetricsLogger metricsLogger;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws java.io.IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;

		final Long requestTimestamp = (Long) request.getAttribute(Constants.RequestKey.REQUEST_START_TIMESTAMP);
		final Map<String, Object> requestHeaders = this.getHeaders(request.getHeaderNames().asIterator(),
				name -> request.getHeader(name));
		// Map<String, String[]> requestParameters = request.getParameterMap();

		String requestBody = ""; // servletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		log.info("Request:{Timestamp: {}, URI: {} {}, QueryParameters: {}, Headers: {}, Body: {}}", requestTimestamp,
				request.getMethod(), request.getRequestURI(), request.getQueryString(), requestHeaders, requestBody);
		final ContentCachingResponseWrapper responseCacheWrapperObject = new ContentCachingResponseWrapper(response);

		chain.doFilter(servletRequest, servletResponse);

		final Map<String, Object> responseHeaders = this.getHeaders(request.getHeaderNames().asIterator(),
				response::getHeader);
		final byte[] responseArray = responseCacheWrapperObject.getContentAsByteArray();
		final String responseBody = new String(responseArray, responseCacheWrapperObject.getCharacterEncoding());
		responseCacheWrapperObject.copyBodyToResponse();

		log.info("Response:{StatusCode: {}, Headers: {}, Body: {}}", response.getStatus(), responseHeaders,
				responseBody);
		this.metricsLogger.log(request);
	}

	private Map<String, Object> getHeaders(final Iterator<String> headerNames,
			final Function<String, Object> headersValue) {
		final Map<String, Object> headers = new HashMap<>();
		if (headerNames != null) {
			String headerName;
			while (headerNames.hasNext()) {
				headerName = headerNames.next();
				headers.put(headerName, headersValue.apply(headerName));
			}
		}
		return headers;
	}

}
