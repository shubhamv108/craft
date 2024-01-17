package code.shubham.app.productorder.workers;

import code.shubham.commons.contexts.CorrelationIDContext;
import code.shubham.commons.kafka.KafkaPublisher;
import code.shubham.commons.models.Event;
import code.shubham.commons.utils.JsonUtils;
import code.shubham.commons.utils.UUIDUtils;
import code.shubham.commons.workers.AbstractWorker;
import code.shubham.app.constants.EventName;
import code.shubham.app.constants.EventType;
import code.shubham.app.driveronboardmodels.DriverOnboardStatusUpdatedEventData;
import code.shubham.app.ordermodels.CreateOrderCommand;
import code.shubham.app.ordermodels.OrderProductDTO;
import code.shubham.app.productorder.doa.repositories.CustomerTypeProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "worker")
public class ProductOrderWorker extends AbstractWorker {

	@Value("${product.order.worker.event.filters.eventname}")
	private Set<String> eventNameFilters;

	@Value("${product.order.worker.failure.kafka.topic.name}")
	private String failureTopic;

	@Value("${product.order.command.kafka.topic.name}")
	private String orderCommandTopicName;

	@Autowired
	protected KafkaPublisher kafkaPublisher;

	private final CustomerTypeProductRepository repository;

	@Autowired
	public ProductOrderWorker(final CustomerTypeProductRepository repository) {
		this.repository = repository;
	}

	@Override
	protected void process(final Event event) {
		final DriverOnboardStatusUpdatedEventData data = JsonUtils.as(event.getData(),
				DriverOnboardStatusUpdatedEventData.class);
		if (!"SHIPPING_OF_TRACKING_DEVICE".equals(data.getDriverOnboard().getStatus())) {
			log.info("[SKIP] Event: {}", event);
			return;
		}

		final List<OrderProductDTO> orderProducts = this.repository.findAllByCustomerType("DRIVER")
			.stream()
			.map(customerTypeProduct -> OrderProductDTO.builder()
				.productId(customerTypeProduct.getProductId())
				.quantity(customerTypeProduct.getQuantity())
				.clientReferenceId(
						UUIDUtils.uuid5(data.getOrderReferenceId() + "_" + customerTypeProduct.getProductId()))
				.build())
			.toList();
		if (orderProducts.isEmpty()) {
			log.info("[SKIP] No product found for customerType: DRIVER");
			return;
		}
		this.kafkaPublisher.send(this.orderCommandTopicName,
				Event.builder()
					.eventName(EventName.CreateOrderCommand.name())
					.eventType(EventType.ORDER.name())
					.data(JsonUtils.get(CreateOrderCommand.builder()
						.products(orderProducts)
						.userId(event.getUserId())
						.customerId(data.getDriverOnboard().getDriverId())
						.customerType("DRIVER")
						.clientReferenceId(data.getOrderReferenceId())
						.build()))
					.uniqueReferenceId(data.getOrderReferenceId())
					.userId(event.getUserId())
					.createdAt(new Date())
					.correlationId(CorrelationIDContext.get())
					.build());
	}

	@Override
	@KafkaListener(topics = "${product.order.worker.driver.onboard.event.kafka.topic.name}",
			groupId = "${product.order.worker.kafka.group.id}")
	public void onReceive(final ConsumerRecord<?, ?> consumerRecord, final Acknowledgment acknowledgment) {
		this.work(consumerRecord, acknowledgment);
	}

	@Override
	protected Set<String> getEventNameFilters() {
		return this.eventNameFilters;
	}

	@Override
	protected String getFailureTopic() {
		return failureTopic;
	}

}
