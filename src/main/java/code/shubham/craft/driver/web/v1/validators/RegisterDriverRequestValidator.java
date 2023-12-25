package code.shubham.craft.driver.web.v1.validators;

import code.shubham.commons.utils.StringUtils;
import code.shubham.commons.validators.AbstractRequestValidator;
import code.shubham.commons.validators.IValidator;
import code.shubham.craft.drivermodels.RegisterDriverRequest;

public class RegisterDriverRequestValidator extends AbstractRequestValidator<RegisterDriverRequest> {

	@Override
	public IValidator<RegisterDriverRequest> validate(final RegisterDriverRequest request) {
		super.validate(request);

		if (StringUtils.isEmpty(request.getVehicleColor()))
			this.putMessage("vehicleColor", MUST_NOT_BE_EMPTY, "vehicleColor");

		if (StringUtils.isEmpty(request.getVehicleColor()))
			this.putMessage("vehicleRegistrationNumber", MUST_NOT_BE_EMPTY, "vehicleRegistrationNumber");

		return this;
	}

}
