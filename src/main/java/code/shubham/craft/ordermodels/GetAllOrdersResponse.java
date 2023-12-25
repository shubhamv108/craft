package code.shubham.craft.ordermodels;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GetAllOrdersResponse {

	@Builder
	@Data
	public static class Order {

		private String orderId;

		private String userId;

		private String status;

	}

	private List<Order> orders;

}
