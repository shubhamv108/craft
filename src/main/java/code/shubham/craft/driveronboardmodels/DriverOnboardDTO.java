package code.shubham.craft.driveronboardmodels;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DriverOnboardDTO {

	private String driverOnboardId;

	private String driverId;

	private String status;

	private String userId;

}
