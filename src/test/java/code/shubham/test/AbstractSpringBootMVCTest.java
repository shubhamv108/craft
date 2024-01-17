package code.shubham.test;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public abstract class AbstractSpringBootMVCTest extends AbstractSpringBootTest {

	@Autowired
	private WebApplicationContext applicationContext;

	protected MockMvc mockMvc;

	private static final Gson GSON = new Gson();

	protected String as(final Object o) {
		return GSON.toJson(o);
	}

	protected void setUp() {
		super.setUp();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.applicationContext).build();
	}

}