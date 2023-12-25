package code.shubham.craft.driveronboard.web.v1.validators;

import code.shubham.commons.utils.StringUtils;
import code.shubham.commons.validators.AbstractRequestValidator;
import code.shubham.commons.validators.IValidator;
import code.shubham.craft.driveronboard.dao.entities.DriverOnboardStatus;
import code.shubham.craft.drivermodels.CompleteOnboardStageRequest;

public class DriverOnboardStageCompletedRequestValidator extends AbstractRequestValidator<CompleteOnboardStageRequest> {

	@Override
	public IValidator<CompleteOnboardStageRequest> validate(final CompleteOnboardStageRequest request) {
		super.validate(request);
		try {
			DriverOnboardStatus completedStatus = DriverOnboardStatus.valueOf(request.getCompletedOnboardStatus());
		}
		catch (Exception exception) {
			this.putMessage("completedOnboardStatus", "no onboarding status with name: %s found",
					request.getCompletedOnboardStatus());
		}

		if (StringUtils.isEmpty(request.getDriverOnboardId()))
			this.putMessage("driverOnboardId", MUST_NOT_BE_EMPTY, "driverOnboardId");

		if (StringUtils.isEmpty(request.getUserId()))
			this.putMessage("userId", MUST_NOT_BE_EMPTY, "userId");

		return this;
	}

}
