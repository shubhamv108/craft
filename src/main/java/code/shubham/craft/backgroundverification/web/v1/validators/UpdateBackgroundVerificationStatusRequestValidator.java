package code.shubham.craft.backgroundverification.web.v1.validators;

import code.shubham.commons.utils.StringUtils;
import code.shubham.commons.validators.AbstractRequestValidator;
import code.shubham.commons.validators.IValidator;
import code.shubham.craft.backgroundverification.dao.entities.BackgroundVerificationStatus;
import code.shubham.craft.backgroundverificatonmodels.UpdateBackgroundVerificationStatusRequest;

public class UpdateBackgroundVerificationStatusRequestValidator
		extends AbstractRequestValidator<UpdateBackgroundVerificationStatusRequest> {

	@Override
	public IValidator<UpdateBackgroundVerificationStatusRequest> validate(
			final UpdateBackgroundVerificationStatusRequest request) {
		super.validate(request);

		if (StringUtils.isEmpty(request.getClientReferenceId()))
			this.putMessage("clientReferenceId", MUST_NOT_BE_EMPTY, "clientReferenceId");

		if (StringUtils.isEmpty(request.getCompletedStatus()))
			this.putMessage("completedStatus", MUST_NOT_BE_EMPTY, "completedStatus");

		try {
			BackgroundVerificationStatus.valueOf(request.getCompletedStatus());
		}
		catch (final Exception exception) {
			this.putMessage("completedStatus", "invalid value for completedStatus: %s", request.getCompletedStatus());
		}
		return this;
	}

}
