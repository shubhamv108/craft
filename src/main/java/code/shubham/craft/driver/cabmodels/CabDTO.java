package code.shubham.craft.driver.cabmodels;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CabDTO {

	private String cabId;

	@NotNull
	@NotEmpty
	@Min(6)
	@Max(40)
	private String registrationNumber;

	@NotNull
	@NotEmpty
	@Min(3)
	@Max(40)
	private String color;

}
