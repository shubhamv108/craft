package code.shubham.app.documentstore.services;

import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.commons.kafka.KafkaPublisher;
import code.shubham.core.blobstore.services.BlobService;
import code.shubham.app.documentstore.dao.entities.Document;
import code.shubham.app.documentstore.dao.repositories.DocumentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DocumentService {

	private final DocumentRepository repository;

	private final BlobService blobService;

	private final KafkaPublisher kafkaPublisher;

	@Autowired
	public DocumentService(final DocumentRepository repository, final BlobService blobService,
			final KafkaPublisher kafkaPublisher) {
		this.repository = repository;
		this.blobService = blobService;
		this.kafkaPublisher = kafkaPublisher;
	}

	public Document save(final Document document) {
		if (!this.blobService.doesBlobExist(document.getBlobId(), document.getOwner()))
			throw new InvalidRequestException("blobId", "blob does not exist");

		final Optional<Document> existing = this.repository.findByOwnerAndName(document.getOwner(), document.getName());
		if (existing.isPresent()) {
			existing.get().setBlobId(document.getBlobId());
			return this.repository.save(existing.get());
		}
		return this.repository.save(document);
	}

	public List<Document> fetchByOwner(final String owner) {
		return this.repository.findAllByOwner(owner);
	}

}
