package code.shubham.app.backgroundverificatonmodels;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateBackgroundVerificationStatusRequest {

	@NotNull
	@NotEmpty
	@Max(40)
	private String clientReferenceId;

	@NotNull
	@NotEmpty
	@Max(64)
	private String completedStatus;

}
