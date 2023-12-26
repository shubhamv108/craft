package code.shubham.craft.driver.cab.web.v1.validation;

import code.shubham.commons.utils.StringUtils;
import code.shubham.commons.validators.AbstractRequestValidator;
import code.shubham.commons.validators.IValidator;
import code.shubham.craft.driver.cabmodels.RegisterCabRequest;

public class RegisterCabRequestValidator extends AbstractRequestValidator<RegisterCabRequest> {

	@Override
	public IValidator<RegisterCabRequest> validate(final RegisterCabRequest request) {
		super.validate(request);

		if (StringUtils.isEmpty(request.getDriverId()))
			this.putMessage("driverId", MUST_NOT_BE_EMPTY, "driverId");

		if (StringUtils.isEmpty(request.getUserId()))
			this.putMessage("userId", MUST_NOT_BE_EMPTY, "userId");

		if (StringUtils.isEmpty(request.getRegistrationNumber()))
			this.putMessage("registrationNumber", MUST_NOT_BE_EMPTY, "registrationNumber");

		if (StringUtils.isEmpty(request.getColor()))
			this.putMessage("color", MUST_NOT_BE_EMPTY, "color");

		return this;
	}

}
