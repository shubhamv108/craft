package code.shubham.core.iammodels;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class GetOrCreateUser {

	@Builder
	@Getter
	@Setter
	public static class Request {

		@NotEmpty
		private String name;

		@NotNull
		@NotEmpty
		@Min(4)
		private String email;

	}

}
