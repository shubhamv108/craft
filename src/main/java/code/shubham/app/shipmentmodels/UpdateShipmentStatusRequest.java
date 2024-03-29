package code.shubham.app.shipmentmodels;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UpdateShipmentStatusRequest {

	@NotNull
	@NotEmpty
	@Min(10)
	@Max(36)
	private String uniqueReferenceId;

	@NotNull
	@NotEmpty
	@Min(10)
	@Max(64)
	private String completedStatus;

}
