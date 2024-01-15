package code.shubham.core.userprofilemodels;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
public class UpdateUserProfileRequest {

	@NotNull
	@NotEmpty
	@Max(value = 256)
	private String address;

}
