package code.shubham.app.driver.web.v1.validators;

import code.shubham.commons.utils.StringUtils;
import code.shubham.commons.validators.AbstractRequestValidator;
import code.shubham.commons.validators.IValidator;
import code.shubham.app.drivermodels.RegisterDriver;

public class RegisterDriverRequestValidator extends AbstractRequestValidator<RegisterDriver.Request> {

	@Override
	public IValidator<RegisterDriver.Request> validate(final RegisterDriver.Request request) {
		super.validate(request);

		if (StringUtils.isEmpty(request.getDrivingLicense()))
			this.putMessage("drivingLicense", MUST_NOT_BE_EMPTY, "drivingLicense");

		if (StringUtils.isEmpty(request.getDrivingLicenseName()))
			this.putMessage("drivingLicenseName", MUST_NOT_BE_EMPTY, "drivingLicenseName");

		if (request.getCab() == null)
			this.putMessage("cab", MUST_NOT_BE_EMPTY, "cab");

		if (request.getCab() == null || StringUtils.isEmpty(request.getCab().getRegistrationNumber()))
			this.putMessage("cab.registrationNumber", MUST_NOT_BE_EMPTY, "cab.registrationNumber");

		if (request.getCab() == null || StringUtils.isEmpty(request.getCab().getColor()))
			this.putMessage("cab.color", MUST_NOT_BE_EMPTY, "cab.color");

		return this;
	}

}
