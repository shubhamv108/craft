package code.shubham.craft.driver.service;

import code.shubham.commons.contexts.CorrelationIDContext;
import code.shubham.commons.contexts.UserContextHolder;
import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.commons.kafka.KafkaPublisher;
import code.shubham.commons.models.Event;
import code.shubham.commons.utils.JsonUtils;
import code.shubham.commons.utils.UUIDUtils;
import code.shubham.commons.utils.Utils;
import code.shubham.craft.constants.EventName;
import code.shubham.craft.constants.EventType;
import code.shubham.craft.driver.dao.entities.Driver;
import code.shubham.craft.driver.dao.entities.DriverStatus;
import code.shubham.craft.driver.dao.repositories.DriverRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverService {

	@Value("${driver.status.sequence}")
	private List<DriverStatus> statusSequence;

	@Value("${driver.kafka.topic.name}")
	private String topicName;

	private final DriverRepository repository;

	private final KafkaPublisher kafkaPublisher;

	@Autowired
	public DriverService(final DriverRepository repository, final KafkaPublisher kafkaPublisher) {
		this.repository = repository;
		this.kafkaPublisher = kafkaPublisher;
	}

	public Driver register(final Driver driver) {
		driver.setStatus(DriverStatus.ONBOARDING);
		final Optional<Driver> existing = this.getByUserId(driver.getUserId());
		if (existing.isPresent())
			throw new InvalidRequestException("email", "User with email: %s already registered as a driver",
					UserContextHolder.get().email());

		driver.setUserId(UserIDContextHolder.get());
		return this.createOrUpdateStatus(driver);
	}

	public Driver updateStatus(final String driverId, final DriverStatus completedStatus) {
		final Driver existing = this.repository.findById(driverId)
			.orElseThrow(() -> new InvalidRequestException("driverId", "no driver found for driverId: %s", driverId));

		if (!completedStatus.name().equals(existing.getStatus().name()))
			throw new InvalidRequestException("completedStatus", "invalid completedStatus: %s", completedStatus.name());

		existing.setStatus(Utils.getNextInSequence(this.statusSequence, existing.getStatus()));
		return this.repository.save(existing);
	}

	public Driver markReadyForRide(final String driverId, final String userId) {
		final Driver driver = this.getByIdAndUserId(driverId, userId);
		if (!driver.getStatus().name().equals(DriverStatus.ACTIVE.name()))
			throw new InvalidRequestException("driverId", "driver with id: %s not active", driverId);
		driver.setAvailableForRide(true);
		return this.repository.save(driver);
	}

	public Driver getByIdAndUserId(final String id, final String userId) {
		return this.repository.findByIdAndUserId(id, userId)
			.orElseThrow(() -> new InvalidRequestException("driverId", "no driver found for driverId: %s", id));
	}

	private Optional<Driver> getByUserId(final String userId) {
		return this.repository.findByUserId(userId);
	}

	@Transactional(rollbackOn = Exception.class)
	private Driver createOrUpdateStatus(final Driver driver) {
		final Driver persisted = this.repository.save(driver);
		this.kafkaPublisher.send(this.topicName,
				Event.builder()
					.eventName(EventName.DriverStatusUpdated.name())
					.eventType(EventType.DRIVER.name())
					.data(JsonUtils.get(persisted))
					.uniqueReferenceId(UUIDUtils.uuid4(persisted.getId() + "_" + persisted.getUpdatedAt()))
					.userId(persisted.getUserId())
					.correlationId(CorrelationIDContext.get())
					.build()); // use eventuate for event sourcing
		return persisted;
	}

}
