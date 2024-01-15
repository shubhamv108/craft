package code.shubham.commons.configurations;

import code.shubham.commons.AbstractSpringBootTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.lang.reflect.Field;

public class PlaceholderConfigurationSpringBootTest extends AbstractSpringBootTest {

	@Autowired
	private PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer;

	@Autowired
	private PlaceholderConfiguration configuration;

	@Test
	void test_propertySourcesPlaceholderConfigurer_bean() throws NoSuchFieldException, IllegalAccessException {
		Assertions.assertNotNull(this.propertySourcesPlaceholderConfigurer);
		this.assertField(this.propertySourcesPlaceholderConfigurer);
	}

	@Test
	void test_propertySourcesPlaceholderConfigurer() throws NoSuchFieldException, IllegalAccessException {
		this.assertField(this.configuration.propertySourcesPlaceholderConfigurer());
	}

	private void assertField(final PropertySourcesPlaceholderConfigurer configuration)
			throws IllegalAccessException, NoSuchFieldException {
		final Field field = configuration.getClass()
			.getSuperclass()
			.getSuperclass()
			.getSuperclass()
			.getDeclaredField("ignoreResourceNotFound");
		field.setAccessible(true);
		Assertions.assertTrue((Boolean) field.get(configuration));
	}

}