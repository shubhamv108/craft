package code.shubham.commons;

import code.shubham.CraftApplication;
import com.google.gson.Gson;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = CraftApplication.class)
public abstract class AbstractTest {

	@Autowired
	private WebApplicationContext applicationContext;

	@Autowired
	EntityManagerRepository entityManagerRepository;

	@Autowired
	protected TestKafkaConsumer kafkaConsumer;

	protected MockMvc mockMvc;

	private static final Gson GSON = new Gson();

	protected String as(final Object o) {
		return GSON.toJson(o);
	}

	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.applicationContext).build();
	}

	protected void truncate(final String table) {
		this.entityManagerRepository.truncateTable(table);
	}

}