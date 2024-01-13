package code.shubham.commons;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public abstract class AbstractWebMVCTest {

	@Autowired
	private WebApplicationContext applicationContext;

	@Autowired
	protected MockMvc mockMvc;

	private static final Gson GSON = new Gson();

	protected String as(final Object o) {
		return GSON.toJson(o);
	}

	protected void setUp() {

	}

}