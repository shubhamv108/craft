package code.shubham.craft.drivermodels;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MarkAvailableForRideRequest {

	@NotNull
	@NotEmpty
	@Min(6)
	@Min(40)
	private String userId;

	@NotNull
	@NotEmpty
	@Min(6)
	@Min(40)
	private String driverId;

}
