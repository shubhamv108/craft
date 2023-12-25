package code.shubham.craft.shipment.web.v1.validators;

import code.shubham.commons.utils.StringUtils;
import code.shubham.commons.validators.AbstractRequestValidator;
import code.shubham.commons.validators.IValidator;
import code.shubham.craft.shipment.dao.entities.ShipmentStatus;
import code.shubham.craft.shipmentmodels.UpdateShipmentStatusRequest;

public class UpdateShipmentStatusRequestValidator extends AbstractRequestValidator<UpdateShipmentStatusRequest> {

	@Override
	public IValidator<UpdateShipmentStatusRequest> validate(final UpdateShipmentStatusRequest request) {
		super.validate(request);

		if (StringUtils.isEmpty(request.getUniqueReferenceId()))
			this.putMessage("uniqueReferenceId", MUST_NOT_BE_EMPTY, "uniqueReferenceId");

		if (StringUtils.isEmpty(request.getCompletedStatus()))
			this.putMessage("completedStatus", MUST_NOT_BE_EMPTY, "completedStatus");

		try {
			final ShipmentStatus status = ShipmentStatus.valueOf(request.getCompletedStatus());
		}
		catch (Exception exception) {
			this.putMessage("completedStatus", "invalid value for completedStatus: %s", request.getCompletedStatus());
		}

		return this;
	}

}
