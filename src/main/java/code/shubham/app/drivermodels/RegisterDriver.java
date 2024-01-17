package code.shubham.app.drivermodels;

import code.shubham.app.driver.cabmodels.CabDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

public class RegisterDriver {

	@Builder
	@Data
	public static class Request {

		@NotNull
		@NotEmpty
		@Min(4)
		@Max(64)
		private String drivingLicenseName;

		@NotNull
		@NotEmpty
		@Min(10)
		@Max(64)
		private String drivingLicense;

		@NotNull
		private CabDTO cab;

	}

	@Builder
	@Data
	public static class Response {

		private String driverId;

		private String drivingLicenseName;

		private String drivingLicense;

		private String userId;

		private CabDTO cab;

	}

}
