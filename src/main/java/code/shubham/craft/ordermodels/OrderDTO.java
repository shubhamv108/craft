package code.shubham.craft.ordermodels;

import code.shubham.craft.order.dao.entities.OrderStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrderDTO {

	@NotNull
	@NotEmpty
	@Min(6)
	@Max(40)
	private String orderId;

	@NotEmpty
	private String customerId;

	@NotEmpty
	private String customerType;

	@NotNull
	@NotEmpty
	@Min(6)
	@Max(40)
	private String userId;

	@NotNull
	@NotEmpty
	private String status;

	@NotNull
	@NotEmpty
	@Min(6)
	@Max(40)
	private String uniqueReferenceId;

}
