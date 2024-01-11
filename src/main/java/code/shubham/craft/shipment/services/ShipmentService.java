package code.shubham.craft.shipment.services;

import code.shubham.commons.contexts.CorrelationIDContext;
import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.commons.models.Event;
import code.shubham.commons.publishers.Publisher;
import code.shubham.core.userprofile.services.UserProfileService;
import code.shubham.commons.utils.JsonUtils;
import code.shubham.commons.utils.Utils;
import code.shubham.craft.constants.EventName;
import code.shubham.craft.constants.EventType;
import code.shubham.craft.ordermodels.OrderProductDTO;
import code.shubham.craft.shipment.dao.entities.Shipment;
import code.shubham.craft.shipment.dao.entities.ShipmentStatus;
import code.shubham.craft.shipment.dao.repositories.ShipmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ShipmentService {

	@Value("${shipment.sequence}")
	private List<ShipmentStatus> statusSequence;

	@Value("${shipment.kafka.topic.name}")
	private String topicName;

	private final ShipmentRepository repository;

	private final Publisher publisher;

	private final UserProfileService profileService;

	@Autowired
	public ShipmentService(final ShipmentRepository repository, final Publisher publisher,
			final UserProfileService profileService) {
		this.repository = repository;
		this.publisher = publisher;
		this.profileService = profileService;
	}

	@Transactional(rollbackFor = Exception.class)
	public List<Shipment> create(final String orderId, final List<OrderProductDTO> products,
			final String trackingNumber, final String carrier, final String userId) {
		return products.stream()
			.map(product -> Shipment.builder()
				.orderId(orderId)
				.deliveryAddress(this.profileService.getAddress(userId))
				.trackingNumber(trackingNumber)
				.carrier(carrier)
				.status(ShipmentStatus.PREPARE_TO_DISPATCH)
				.uniqueReferenceId(product.getClientReferenceId())
				.build())
			.map(this::save)
			.filter(Objects::nonNull)
			.toList();
	}

	@Transactional(rollbackFor = Exception.class)
	public Shipment updateStatus(final String uniqueReferenceId, final ShipmentStatus completedStatus) {
		final Shipment shipment = this.repository.findByUniqueReferenceId(uniqueReferenceId)
			.orElseThrow(() -> new InvalidRequestException("uniqueReferenceId",
					"No shipment found for uniqueReferenceId: %s", uniqueReferenceId));

		if (ShipmentStatus.DELIVERED.name().equals(shipment.getStatus().name()))
			throw new InvalidRequestException("uniqueReferenceId", "shipment delivered for uniqueReferenceId: %s",
					uniqueReferenceId);

		if (!completedStatus.equals(shipment.getStatus()))
			throw new InvalidRequestException("completedStatus", "invalid status in request: %s",
					completedStatus.name());

		shipment.setStatus(Utils.getNextInSequence(this.statusSequence, completedStatus));
		return this.save(shipment);
	}

	public Shipment save(final Shipment shipment) {
		Shipment updated = null;
		try {
			updated = this.repository.save(shipment);
			this.publisher.send(topicName,
					Event.builder()
						.eventName(EventName.ShipmentStatusUpdated.name())
						.eventType(EventType.SHIPMENT.name())
						.data(JsonUtils.get(updated))
						.createdAt(new Date())
						.userId(UserIDContextHolder.get())
						.uniqueReferenceId(updated.getUniqueReferenceId())
						.correlationId(CorrelationIDContext.get())
						.build());
		}
		catch (final DataIntegrityViolationException exception) {
			log.error("Shipment entry already present for product client reference id: {}",
					shipment.getUniqueReferenceId());
		}
		return updated;
	}

}
