package code.shubham.commons.validators;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractValidatorTest {

	private AbstractValidator<String> validator;

	@BeforeEach
	void setUp() {
		this.validator = new AbstractValidator<>() {
			@Override
			public IValidator<String> validate(String object) {
				return null;
			}

			@Override
			public IValidator<String> validateOrThrowException(String object) {
				return null;
			}
		};
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void putMessage() {
		boolean response = this.validator.putMessage("key", "%s", "value1");

		Assertions.assertTrue(response);
		Assertions.assertTrue(this.validator.hasMessages());
		Assertions.assertEquals(this.validator.getResult().get("key").stream().findFirst().get(), "value1");

		response = this.validator.putMessage("key", "%s", "value2");
		Assertions.assertTrue(response);
		Assertions.assertEquals(this.validator.getResult().get("key").stream().skip(1).findFirst().get(), "value2");

	}

}