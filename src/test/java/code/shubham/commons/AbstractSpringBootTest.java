package code.shubham.commons;

import code.shubham.CraftApplication;
import code.shubham.commons.contexts.RoleContextHolder;
import code.shubham.commons.contexts.UserIDContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest(classes = CraftApplication.class)
public abstract class AbstractSpringBootTest {

	protected TestKafkaConsumer kafkaConsumer;

	@Autowired
	private EntityManagerRepository entityManagerRepository;

	protected void setUp() {
		RoleContextHolder.set(Set.of());
		UserIDContextHolder.set(null);
	}

	protected void truncate(final String table) {
		this.entityManagerRepository.truncateTable(table);
	}

}
