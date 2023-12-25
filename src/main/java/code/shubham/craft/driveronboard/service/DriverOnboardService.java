package code.shubham.craft.driveronboard.service;

import code.shubham.commons.contexts.CorrelationIDContext;
import code.shubham.commons.contexts.RoleContextHolder;
import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.commons.kafka.KafkaPublisher;
import code.shubham.commons.models.Event;
import code.shubham.commons.utils.JsonUtils;
import code.shubham.commons.utils.UUIDUtils;
import code.shubham.commons.utils.Utils;
import code.shubham.craft.constants.EventName;
import code.shubham.craft.constants.EventType;
import code.shubham.craft.driveronboard.dao.entities.DriverOnboardBackgroundVerification;
import code.shubham.craft.driveronboard.dao.entities.DriverOnboardOrder;
import code.shubham.craft.driveronboard.dao.entities.DriverOnboardStatus;
import code.shubham.craft.driver.service.DriverService;
import code.shubham.craft.driveronboard.dao.entities.DriverOnboard;
import code.shubham.craft.driveronboard.dao.repositories.DriverOnboardBackgroundVerificationRepository;
import code.shubham.craft.driveronboard.dao.repositories.DriverOnboardOrderRepository;
import code.shubham.craft.driveronboard.dao.repositories.DriverOnboardRepository;
import code.shubham.craft.driveronboardmodels.DriverOnboardDTO;
import code.shubham.craft.driveronboardmodels.DriverOnboardStatusUpdatedEventData;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static code.shubham.craft.driveronboard.dao.entities.DriverOnboardStatus.BACKGROUND_VERIFICATION;
import static code.shubham.craft.driveronboard.dao.entities.DriverOnboardStatus.DOCUMENT_COLLECTION;
import static code.shubham.craft.driveronboard.dao.entities.DriverOnboardStatus.ONBOARDING_COMPLETED;
import static code.shubham.craft.driveronboard.dao.entities.DriverOnboardStatus.SHIPPING_OF_TRACKING_DEVICE;

@Service
public class DriverOnboardService {

	@Value("${kafka.topic.name}")
	private String topicName;

	@Value("${driver.onboard.sequence}")
	private List<DriverOnboardStatus> onboardingSequence;

	@Value("${driver.onboard.admin.updatable.status}")
	private Set<DriverOnboardStatus> adminVerifiableStatuses;

	private final DriverOnboardRepository repository;

	private final KafkaPublisher kafkaPublisher;

	private final DriverService driverService;

	private final DriverOnboardBackgroundVerificationRepository driverOnboardBackgroundVerificationRepository;

	private final DriverOnboardOrderRepository driverOnboardOrderRepository;

	@Autowired
	public DriverOnboardService(final DriverOnboardRepository repository, final KafkaPublisher kafkaPublisher,
			final DriverService driverService,
			final DriverOnboardBackgroundVerificationRepository driverOnboardBackgroundVerificationRepository,
			final DriverOnboardOrderRepository driverOnboardOrderRepository) {
		this.repository = repository;
		this.kafkaPublisher = kafkaPublisher;
		this.driverService = driverService;
		this.driverOnboardBackgroundVerificationRepository = driverOnboardBackgroundVerificationRepository;
		this.driverOnboardOrderRepository = driverOnboardOrderRepository;
	}

	public DriverOnboard create(final String driverId, final String userId, final String clientReferenceId) {
		return this.repository.findByDriverId(driverId)
			.orElse(this.repository.save(DriverOnboard.builder()
				.driverId(driverId)
				.status(DOCUMENT_COLLECTION)
				.clientReferenceId(clientReferenceId)
				.userId(userId)
				.build()));

	}

	public DriverOnboard updateStatus(final DriverOnboardStatus completedStatus, final String driverOnboardId,
			final String userId) {
		if (this.adminVerifiableStatuses.contains(completedStatus) && !RoleContextHolder.isAdmin())
			throw new InvalidRequestException("completedStatus", "%s is admin updatable status",
					completedStatus.name());

		Utils.validateUserOrThrowException(userId);

		return this.markCompletedStatus(completedStatus, driverOnboardId, userId);
	}

	public List<DriverOnboard> fetchAllByDriverIdAndUserId(final String driverId, final String userId) {
		Utils.validateUserOrThrowException(userId);
		return this.repository.findAllByDriverIdAndUserId(driverId, userId);
	}

	public DriverOnboard markCompletedStatusByUniqueReferenceId(final DriverOnboardStatus completedStatus,
			final String uniqueReferenceId, final String userId) {
		final String driverOnboardId = this.getDriverOnboardIdFromUniqueReferenceId(completedStatus, uniqueReferenceId);
		return markCompletedStatus(completedStatus, driverOnboardId, userId);
	}

	public DriverOnboard markCompletedStatus(final DriverOnboardStatus completedStatus, final String driverOnboardId,
			final String userId) {
		final DriverOnboard onboard = this.repository.findByIdAndUserId(driverOnboardId, userId)
			.orElseThrow(() -> new InvalidRequestException("driverOnboardId",
					"No onboarding state for driverOnboardId: %s", driverOnboardId));

		if (ONBOARDING_COMPLETED.name().equals(onboard.getStatus().name()))
			throw new InvalidRequestException("driverOnboardId", "onboarding completed for onboard id: %s",
					driverOnboardId);

		if (!completedStatus.equals(onboard.getStatus()))
			throw new InvalidRequestException("status", "Invalid onboard status in request: %s",
					completedStatus.name());

		onboard.setStatus(Utils.getNextInSequence(this.onboardingSequence, completedStatus));
		return this.updateStatus(onboard);
	}

	@Transactional(rollbackOn = Exception.class)
	private DriverOnboard updateStatus(final DriverOnboard onboard) {
		final DriverOnboard updated = this.repository.save(onboard);

		final DriverOnboardStatusUpdatedEventData event = DriverOnboardStatusUpdatedEventData.builder()
			.driverOnboard(DriverOnboardDTO.builder()
				.driverId(updated.getDriverId())
				.status(updated.getStatus().name())
				.driverOnboardId(updated.getId())
				.userId(updated.getUserId())
				.build())
			.build();

		if (updated.getStatus().name().equals(BACKGROUND_VERIFICATION.name())) {
			final DriverOnboardBackgroundVerification driverOnboardBackgroundVerification = this.driverOnboardBackgroundVerificationRepository
				.save(DriverOnboardBackgroundVerification.builder()
					.driverOnboardId(updated.getId())
					.backgroundVerificationReferenceId(UUIDUtils.generate())
					.build());
			event.setBackgroundVerificationId(
					driverOnboardBackgroundVerification.getBackgroundVerificationReferenceId());
		}

		if (updated.getStatus().name().equals(SHIPPING_OF_TRACKING_DEVICE.name())) {
			final DriverOnboardOrder driverOnboardOrder = this.driverOnboardOrderRepository
				.save(DriverOnboardOrder.builder()
					.driverOnboardId(updated.getId())
					.orderReferenceId(UUIDUtils.generate())
					.build());
			event.setOrderReferenceId(driverOnboardOrder.getOrderReferenceId());
		}

		this.kafkaPublisher.send(this.topicName,
				Event.builder()
					.eventName(EventName.DriverOnboardStatusUpdated.name())
					.eventType(EventType.DRIVER_ONBOARD.name())
					.data(JsonUtils.get(event))
					.uniqueReferenceId(updated.getClientReferenceId())
					.userId(updated.getUserId())
					.createdAt(new Date())
					.correlationId(CorrelationIDContext.get())
					.build());
		return updated;
	}

	private String getDriverOnboardIdFromUniqueReferenceId(final DriverOnboardStatus completedStatus,
			final String uniqueReferenceId) {
		switch (completedStatus) {
			case BACKGROUND_VERIFICATION:
				return this.driverOnboardBackgroundVerificationRepository
					.findByBackgroundVerificationReferenceId(uniqueReferenceId)
					.map(DriverOnboardBackgroundVerification::getDriverOnboardId)
					.orElseThrow(() -> new InvalidRequestException("uniqueReferenceId",
							"No background verification with %s found for any diverOnboarding", uniqueReferenceId));
			case SHIPPING_OF_TRACKING_DEVICE:
				return this.driverOnboardOrderRepository.findByOrderReferenceId(uniqueReferenceId)
					.map(DriverOnboardOrder::getDriverOnboardId)
					.orElseThrow(() -> new InvalidRequestException("uniqueReferenceId",
							"No order with %s found for any diverOnboarding", uniqueReferenceId));
			default:
				throw new InvalidRequestException("completedStatus", "No support for updating completedStatus: %s",
						completedStatus.name());
		}
	}

}
