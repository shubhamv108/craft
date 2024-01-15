package code.shubham.craft.ordermodels;

import code.shubham.craft.order.dao.entities.Order;
import code.shubham.craft.order.dao.entities.OrderProduct;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class OrderEventData {

	@NotNull
	private OrderDTO order;

	private List<OrderProductDTO> products;

}
