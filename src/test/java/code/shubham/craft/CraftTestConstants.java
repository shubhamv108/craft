package code.shubham.craft;

import code.shubham.commons.utils.UUIDUtils;

public interface CraftTestConstants {

	String DRIVER_ID = "b2dca92b-fe74-47f6-a369-8940f6291d72";

	String APPLICANT_TYPE_DRIVER = "DRIVER";

	String BACKGROUND_VERIFICATION_CLIENT_REFERENCE_ID = "a2dca92b-fe74-47f6-a369-8940f6291e72";

	String DRIVER_ONBOARD_ID = "b2dca92b-fe74-47f6-a361-8940f6291d72";

	String DRIVER_ONBOARD_CLIENT_REFERENCE_ID = "a2dca91b-fe74-47f6-a369-8940f6291e72";

	String SHIPMENT_UNIQUE_REFERENCE_ID = UUIDUtils
		.uuid5(CraftTestConstants.ORDER_UNIQUE_REFERENCE_ID + "_" + CraftTestConstants.PRODUCT_ID);

	String ORDER_ID = "a2dca91b-fe74-47f6-a369-8940f6291e42";

	String ORDER_UNIQUE_REFERENCE_ID = "a2dca92c-fe74-47f6-a369-8940f6291e42";

	String ORDER_PRODUCT_UNIQUE_REFERENCE_ID = "a2dca93c-fe74-47f6-a369-8940f6291e42";

	String PRODUCT_ID = "TRACKING_DEVICE";

	String VEHICLE_REGISTRATION_NUMBER = "ka01ab1234";

	String VEHICLE_COLOR = "white";

	String DRIVING_LICENSE_NAME = "Shubham";

	String DRIVING_LICENSE_NUMBER = "ka0174923874";

}
