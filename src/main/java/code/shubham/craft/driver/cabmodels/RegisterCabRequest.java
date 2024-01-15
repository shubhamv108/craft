package code.shubham.craft.driver.cabmodels;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegisterCabRequest {

	@NotNull
	@NotEmpty
	@Min(6)
	@Max(12)
	private String registrationNumber;

	@NotNull
	@NotEmpty
	@Min(3)
	@Max(12)
	private String color;

	@NotNull
	@NotEmpty
	@Min(6)
	@Max(24)
	private String driverId;

	@NotNull
	@NotEmpty
	@Min(6)
	@Max(40)
	private String userId;

}
