package code.shubham.craft.drivermodels;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CompleteOnboardStageRequest {

	@NotNull
	@NotEmpty
	@Max(64)
	private String completedOnboardStatus;

	@NotNull
	@NotEmpty
	@Min(10)
	@Max(36)
	private String driverOnboardId;

	@NotNull
	@NotEmpty
	@Min(10)
	@Max(36)
	private String userId;

}
