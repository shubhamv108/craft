package code.shubham.app.documentstore.web.v1.controllers;

import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.commons.utils.ResponseUtils;
import code.shubham.commons.utils.Utils;
import code.shubham.core.blobstore.services.BlobService;
import code.shubham.app.documentstore.web.v1.validators.SaveDocumentRequestValidator;
import code.shubham.app.documentstore.services.DocumentService;
import code.shubham.app.documentstore.dao.entities.Document;
import code.shubham.app.documentstoremodels.SaveDocumentRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.http.HttpStatusCode;

import java.util.Map;

@RestController
@RequestMapping("/v1/documents")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Document Store")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
public class DocumentStoreController {

	@Value("${documentstore.bucket.name}")
	private String defaultBucket;

	private final BlobService blobService;

	private final DocumentService service;

	@Autowired
	public DocumentStoreController(final BlobService blobService, final DocumentService service) {
		this.blobService = blobService;
		this.service = service;
	}

	@GetMapping("/uploadURL")
	public ResponseEntity<?> getDocumentUploadURL(@RequestBody(required = false) final Map<String, String> metadata) {
		return ResponseUtils.getDataResponseEntity(200, this.blobService
			.getPreSignedUploadUrl(UserIDContextHolder.get(), this.defaultBucket, UserIDContextHolder.get(), metadata));
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody final SaveDocumentRequest request) {
		new SaveDocumentRequestValidator().validateOrThrowException(request);

		Utils.validateUserOrThrowException(request.getUserId());

		return ResponseUtils.getDataResponseEntity(HttpStatusCode.CREATED,
				this.service.save(Document.builder()
					.owner(request.getUserId())
					.name(request.getName())
					.blobId(request.getBlobId())
					.build()));
	}

}
