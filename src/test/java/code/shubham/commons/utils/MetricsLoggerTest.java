package code.shubham.commons.utils;

import code.shubham.commons.CommonTestEventUtils;
import code.shubham.test.AbstractSpringBootTest;
import code.shubham.commons.workers.AbstractWorker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MetricsLoggerTest extends AbstractSpringBootTest {

	@Autowired
	private MetricsLogger metricsLogger;

	@Test
	void log() {
		final MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRequestURI("/test");
		request.setAttribute("requestStartTimestamp", System.currentTimeMillis());

		this.metricsLogger.log(request);

		final String output = outContent.toString();
		assertTrue(output.contains("api.execution.duration service=craft,module=web,api=/test "));
	}

	@Test
	void test_Log_event() {
		this.metricsLogger.log(CommonTestEventUtils.getEmptyEvent(), System.currentTimeMillis(), AbstractWorker.class);

		final String output = outContent.toString();
		assertTrue(output.contains(
				"event.handler.execution.duration service=craft,module=web,handler=AbstractWorker,type=type,name=name "));
	}

}