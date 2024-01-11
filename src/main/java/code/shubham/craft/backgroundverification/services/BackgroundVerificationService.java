package code.shubham.craft.backgroundverification.services;

import code.shubham.commons.contexts.CorrelationIDContext;
import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.commons.models.Event;
import code.shubham.commons.publishers.Publisher;
import code.shubham.commons.utils.JsonUtils;
import code.shubham.commons.utils.Utils;
import code.shubham.craft.backgroundverification.dao.entities.BackgroundVerification;
import code.shubham.craft.backgroundverification.dao.entities.BackgroundVerificationStatus;
import code.shubham.craft.backgroundverification.dao.repositories.BackgroundVerificationRepository;
import code.shubham.craft.constants.EventName;
import code.shubham.craft.constants.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class BackgroundVerificationService {

	@Value("${background.verification.sequence}")
	private List<BackgroundVerificationStatus> statusSequence;

	@Value("${background.verification.kafka.topic.name}")
	private String topicName;

	private final BackgroundVerificationRepository repository;

	private final Publisher publisher;

	@Autowired
	public BackgroundVerificationService(final BackgroundVerificationRepository repository, final Publisher publisher) {
		this.repository = repository;
		this.publisher = publisher;
	}

	public List<BackgroundVerification> fetchAllByUserId(String userId) {
		return this.repository.findAllByUserId(userId);
	}

	@Transactional
	public BackgroundVerification getOrCreate(final String applicantId, final String applicantType, final String userId,
			final String clientReferenceId) {
		return this.repository.findByClientReferenceId(clientReferenceId)
			.orElse(this.save(BackgroundVerification.builder()
				.applicantId(applicantId)
				.applicantType(applicantType)
				.status(BackgroundVerificationStatus.ONGOING)
				.clientReferenceId(clientReferenceId)
				.userId(userId)
				.build()));
	}

	@Transactional
	public BackgroundVerification updateStatus(final String clientReferenceId,
			final BackgroundVerificationStatus completedStatus) {
		final BackgroundVerification backgroundVerification = this.repository.findByClientReferenceId(clientReferenceId)
			.orElseThrow(() -> new InvalidRequestException("clientReferenceId",
					"No background verification initiated with clientReferenceId: %s", clientReferenceId));

		if (BackgroundVerificationStatus.COMPLETED.equals(backgroundVerification.getStatus()))
			throw new InvalidRequestException("clientReferenceId",
					"background verification completed for clientReferenceId: %s", clientReferenceId);

		if (!completedStatus.equals(backgroundVerification.getStatus()))
			throw new InvalidRequestException("completedStatus", "invalid status in request: %s",
					completedStatus.name());

		backgroundVerification.setStatus(Utils.getNextInSequence(this.statusSequence, completedStatus));
		return this.save(backgroundVerification);
	}

	public BackgroundVerification save(final BackgroundVerification backgroundVerification) {
		final BackgroundVerification updated = this.repository.save(backgroundVerification);
		this.publisher.send(topicName,
				Event.builder()
					.eventName(EventName.BackgroundVerificationStatusUpdated.name())
					.eventType(EventType.BACKGROUND_VERIFICATION.name())
					.data(JsonUtils.get(updated))
					.createdAt(new Date())
					.userId(backgroundVerification.getUserId())
					.uniqueReferenceId(updated.getClientReferenceId())
					.correlationId(CorrelationIDContext.get())
					.build());
		return updated;
	}

}
