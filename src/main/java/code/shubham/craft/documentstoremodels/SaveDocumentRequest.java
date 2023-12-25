package code.shubham.craft.documentstoremodels;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SaveDocumentRequest {

	@NotNull
	@NotEmpty
	@Max(64)
	private String name;

	@NotNull
	@NotEmpty
	@Min(36)
	@Max(36)
	private String blobId;

}
