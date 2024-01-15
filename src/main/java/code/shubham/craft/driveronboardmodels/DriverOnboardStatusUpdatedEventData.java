package code.shubham.craft.driveronboardmodels;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DriverOnboardStatusUpdatedEventData {

	private DriverOnboardDTO driverOnboard;

	private String backgroundVerificationId;

	private String orderReferenceId;

}
