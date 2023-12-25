package code.shubham.commons.dao.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class PersistenceConfig {

	@Bean
	public AuditorAware<String> auditorProvider() {
		return new UserIdAuditorAware();
	}

}
