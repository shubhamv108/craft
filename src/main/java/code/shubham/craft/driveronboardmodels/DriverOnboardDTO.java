package code.shubham.craft.driveronboardmodels;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DriverOnboardDTO {

	private String driverOnboardId;

	private String driverId;

	private String status;

	private String userId;

}
