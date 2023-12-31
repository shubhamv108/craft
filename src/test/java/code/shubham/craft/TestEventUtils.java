package code.shubham.craft;

import code.shubham.commons.CommonTestConstants;
import code.shubham.commons.contexts.CorrelationIDContext;
import code.shubham.commons.models.Event;
import code.shubham.commons.utils.JsonUtils;
import code.shubham.craft.backgroundverification.dao.entities.BackgroundVerification;
import code.shubham.craft.constants.EventName;
import code.shubham.craft.constants.EventType;
import code.shubham.craft.driver.dao.entities.Driver;
import code.shubham.craft.driveronboard.dao.entities.DriverOnboard;
import code.shubham.craft.driveronboard.dao.entities.DriverOnboardStatus;
import code.shubham.craft.driveronboardmodels.DriverOnboardDTO;
import code.shubham.craft.driveronboardmodels.DriverOnboardStatusUpdatedEventData;
import code.shubham.craft.order.dao.entities.Order;
import code.shubham.craft.order.dao.entities.OrderProduct;
import code.shubham.craft.order.dao.entities.OrderProductStatus;
import code.shubham.craft.ordermodels.OrderDTO;
import code.shubham.craft.ordermodels.OrderEventData;
import code.shubham.craft.ordermodels.OrderProductDTO;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class TestEventUtils {

	public static Event getBackgoundVerificationStatusUpdatedEvent(
			final BackgroundVerification backgroundVerification) {
		return Event.builder()
			.eventName(EventName.BackgroundVerificationStatusUpdated.name())
			.eventType(EventType.BACKGROUND_VERIFICATION.name())
			.data(JsonUtils.get(backgroundVerification))
			.createdAt(new Date())
			.userId(backgroundVerification.getUserId())
			.uniqueReferenceId(backgroundVerification.getClientReferenceId())
			.correlationId(CorrelationIDContext.get())
			.build();
	}

	public static Event getDriverStatusUpdatedEvent(final Driver driver) {
		return Event.builder()
			.eventName(EventName.DriverOnboardStatusUpdated.name())
			.eventType(EventType.DRIVER_ONBOARD.name())
			.data(JsonUtils.get(driver))
			.uniqueReferenceId(driver.getId() + "_")
			.userId(driver.getUserId())
			.createdAt(new Date())
			.correlationId(CorrelationIDContext.get())
			.build();
	}

	public static Event getDriverOnboardStatusUpdatedEvent(final DriverOnboard driverOnboard) {
		// DriverOnboard driverOnboard = this.repository.save(DriverOnboard.builder()
		// .driverId(CraftTestConstants.DRIVER_ID)
		// .userId(CommonTestConstants.USER_ID)
		// .clientReferenceId(CraftTestConstants.DRIVER_ONBOARD_CLIENT_REFERENCE_ID)
		// .status(DriverOnboardStatus.SHIPPING_OF_TRACKING_DEVICE)
		// .build());
		final DriverOnboardStatusUpdatedEventData event = DriverOnboardStatusUpdatedEventData.builder()
			.driverOnboard(DriverOnboardDTO.builder()
				.driverId(driverOnboard.getDriverId())
				.status(driverOnboard.getStatus().name())
				.driverOnboardId(driverOnboard.getId())
				.userId(driverOnboard.getUserId())
				.build())
			.build();
		return Event.builder()
			.eventName(EventName.DriverOnboardStatusUpdated.name())
			.eventType(EventType.DRIVER_ONBOARD.name())
			.data(JsonUtils.get(event))
			.uniqueReferenceId(driverOnboard.getClientReferenceId())
			.userId(driverOnboard.getUserId())
			.createdAt(new Date())
			.correlationId(CorrelationIDContext.get())
			.build();
	}

	public static Event getOrderEvent(final Order order, final EventName eventName) {
		return Event.builder()
			.eventName(eventName.name())
			.eventType(EventType.ORDER.name())
			.data(JsonUtils.get(OrderEventData.builder()
				.order(OrderDTO.builder()
					.orderId(order.getId())
					.status(order.getStatus().name())
					.customerId(order.getCustomerId())
					.customerType(order.getCustomerType())
					.userId(order.getUserId())
					.uniqueReferenceId(order.getUniqueReferenceId())
					.build())
				.products(List.of(OrderProductDTO.builder()
					.productId(CraftTestConstants.PRODUCT_ID)
					.quantity(1)
					.status(OrderProductStatus.CREATED.name())
					.clientReferenceId(CraftTestConstants.SHIPMENT_UNIQUE_REFERENCE_ID)
					.build()))
				.build()))
			.createdAt(new Date())
			.userId(order.getUserId())
			.uniqueReferenceId(order.getUniqueReferenceId())
			.correlationId(CorrelationIDContext.get())
			.build();
	}

}
