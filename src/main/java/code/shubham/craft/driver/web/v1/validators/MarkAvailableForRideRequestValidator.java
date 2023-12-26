package code.shubham.craft.driver.web.v1.validators;

import code.shubham.commons.utils.StringUtils;
import code.shubham.commons.validators.AbstractRequestValidator;
import code.shubham.commons.validators.IValidator;
import code.shubham.craft.drivermodels.MarkAvailableForRideRequest;

public class MarkAvailableForRideRequestValidator extends AbstractRequestValidator<MarkAvailableForRideRequest> {

	@Override
	public IValidator<MarkAvailableForRideRequest> validate(final MarkAvailableForRideRequest request) {
		super.validate(request);

		if (StringUtils.isEmpty(request.getDriverId()))
			this.putMessage("driverId", MUST_NOT_BE_EMPTY, "driverId");

		if (StringUtils.isEmpty(request.getUserId()))
			this.putMessage("userId", MUST_NOT_BE_EMPTY, "userId");

		if (StringUtils.isEmpty(request.getVehicleRegistrationNumber()))
			this.putMessage("vehicleRegistrationNumber", MUST_NOT_BE_EMPTY, "vehicleRegistrationNumber");

		return this;
	}

}
