package code.shubham.craft.drivermodels;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegisterDriverResponse {

	private String vehicleRegistrationNumber;

	private String vehicleColor;

	private String driverId;

	private String drivingLicense;

	private String userId;

}
