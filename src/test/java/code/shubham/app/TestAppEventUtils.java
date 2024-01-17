package code.shubham.app;

import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.contexts.CorrelationIDContext;
import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.commons.models.Event;
import code.shubham.commons.utils.JsonUtils;
import code.shubham.commons.utils.UUIDUtils;
import code.shubham.app.backgroundverification.dao.entities.BackgroundVerification;
import code.shubham.app.constants.EventName;
import code.shubham.app.constants.EventType;
import code.shubham.app.driver.dao.entities.Driver;
import code.shubham.app.driveronboard.dao.entities.DriverOnboard;
import code.shubham.app.driveronboardmodels.DriverOnboardDTO;
import code.shubham.app.driveronboardmodels.DriverOnboardStatusUpdatedEventData;
import code.shubham.app.order.dao.entities.Order;
import code.shubham.app.order.dao.entities.OrderProductStatus;
import code.shubham.app.ordermodels.CreateOrderCommand;
import code.shubham.app.ordermodels.OrderDTO;
import code.shubham.app.ordermodels.OrderEventData;
import code.shubham.app.ordermodels.OrderProductDTO;
import code.shubham.app.shipment.dao.entities.Shipment;
import code.shubham.app.shipment.dao.entities.ShipmentStatus;

import java.util.Date;
import java.util.List;

public class TestAppEventUtils {

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
		final DriverOnboardStatusUpdatedEventData event = DriverOnboardStatusUpdatedEventData.builder()
			.driverOnboard(DriverOnboardDTO.builder()
				.driverId(driverOnboard.getDriverId())
				.status(driverOnboard.getStatus().name())
				.driverOnboardId(driverOnboard.getId())
				.userId(driverOnboard.getUserId())
				.build())
			.build();
		event.setBackgroundVerificationId(TestAppConstants.BACKGROUND_VERIFICATION_CLIENT_REFERENCE_ID);
		event.setOrderReferenceId(TestAppConstants.ORDER_UNIQUE_REFERENCE_ID);
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
					.productId(TestAppConstants.PRODUCT_ID)
					.quantity(1)
					.status(OrderProductStatus.CREATED.name())
					.clientReferenceId(TestAppConstants.SHIPMENT_UNIQUE_REFERENCE_ID)
					.build()))
				.build()))
			.createdAt(new Date())
			.userId(order.getUserId())
			.uniqueReferenceId(order.getUniqueReferenceId())
			.correlationId(CorrelationIDContext.get())
			.build();
	}

	public static Event getCreateOrderCommandEvent() {
		return Event.builder()
			.eventName(EventName.CreateOrderCommand.name())
			.eventType(EventType.ORDER.name())
			.data(JsonUtils.get(CreateOrderCommand.builder()
				.products(List.of(OrderProductDTO.builder()
					.productId(TestAppConstants.PRODUCT_ID)
					.quantity(1)
					.clientReferenceId(UUIDUtils
						.uuid5(TestAppConstants.ORDER_UNIQUE_REFERENCE_ID + "_" + TestAppConstants.PRODUCT_ID))
					.build()))
				.userId(TestCommonConstants.USER_ID)
				.customerId(TestAppConstants.DRIVER_ID)
				.customerType("DRIVER")
				.clientReferenceId(TestAppConstants.ORDER_UNIQUE_REFERENCE_ID)
				.build()))
			.uniqueReferenceId(TestAppConstants.ORDER_UNIQUE_REFERENCE_ID)
			.userId(TestCommonConstants.USER_ID)
			.createdAt(new Date())
			.correlationId(CorrelationIDContext.get())
			.build();
	}

	public static Event getShipmentStatusUpdatedEvent(ShipmentStatus status, String orderId) {
		return Event.builder()
			.eventName(EventName.ShipmentStatusUpdated.name())
			.eventType(EventType.SHIPMENT.name())
			.data(JsonUtils.get(Shipment.builder()
				.uniqueReferenceId(TestAppConstants.SHIPMENT_UNIQUE_REFERENCE_ID)
				.orderId(orderId)
				.status(status)
				.build()))
			.createdAt(new Date())
			.userId(UserIDContextHolder.get())
			.uniqueReferenceId(TestAppConstants.SHIPMENT_UNIQUE_REFERENCE_ID)
			.correlationId(CorrelationIDContext.get())
			.build();
	}

}
