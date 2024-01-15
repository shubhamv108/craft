package code.shubham.commons;

import code.shubham.CraftApplication;
import code.shubham.commons.contexts.RoleContextHolder;
import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.commons.kafka.KafkaPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest(classes = CraftApplication.class)
public abstract class AbstractSpringBootTest {

	protected TestKafkaConsumer kafkaConsumer;

	@Autowired
	protected KafkaPublisher kafkaPublisher;

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
