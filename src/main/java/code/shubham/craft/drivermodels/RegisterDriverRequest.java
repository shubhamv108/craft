package code.shubham.craft.drivermodels;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegisterDriverRequest {

	@NotNull
	@NotEmpty
	@Min(10)
	@Max(64)
	private String vehicleRegistrationNumber;

	@NotNull
	@NotEmpty
	@Min(10)
	@Max(64)
	private String vehicleColor;

	@NotNull
	@NotEmpty
	@Min(10)
	@Max(64)
	private String drivingLicense;

}
