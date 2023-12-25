package code.shubham.craft.driveronboardmodels;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DriverOnboardStatusUpdatedEventData {

	private DriverOnboardDTO driverOnboard;

	private String backgroundVerificationId;

	private String orderReferenceId;

}
