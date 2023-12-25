package code.shubham.core.blobstoremodels;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateBlobResponse {

	@NotNull
	private String blobId;

	@NotNull
	private String uploadUrl;

}
