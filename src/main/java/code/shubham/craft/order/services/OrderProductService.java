package code.shubham.craft.order.services;

import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.commons.utils.Utils;
import code.shubham.craft.order.dao.entities.OrderProduct;
import code.shubham.craft.order.dao.entities.OrderProductStatus;
import code.shubham.craft.order.dao.repositories.OrderProductRepository;
import code.shubham.craft.ordermodels.OrderProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderProductService {

	@Value("${order.product.sequence}")
	private List<OrderProductStatus> statusSequence;

	private final OrderProductRepository repository;

	@Autowired
	public OrderProductService(final OrderProductRepository repository) {
		this.repository = repository;
	}

	public List<OrderProduct> save(final String orderId, final List<OrderProductDTO> products) {
		final List<OrderProduct> orderProducts = products.stream()
			.map(productOrder -> OrderProduct.builder()
				.orderId(orderId)
				.quantity(productOrder.getQuantity())
				.status(OrderProductStatus.CREATED)
				.productId(productOrder.getProductId())
				.uniqueReferenceId(productOrder.getClientReferenceId())
				.build())
			.toList();

		log.info("[STARTED] persisting products for order id: {}", orderId);
		final var persisted = this.repository.saveAll(orderProducts);
		log.info("[COMPLETED] persisted products for order id: {}", orderId);
		return persisted;
	}

	public OrderProduct updateStatus(final OrderProductStatus completedStatus, final String uniqueReferenceId) {
		final OrderProduct orderProduct = this.repository.findByUniqueReferenceId(uniqueReferenceId)
			.orElseThrow(() -> new InvalidRequestException("uniqueReferenceId",
					"no product with uniqueReferenceId %s found for order", uniqueReferenceId));
		if (OrderProductStatus.COMPLETED.name().equals(orderProduct.getStatus().name()))
			throw new InvalidRequestException("uniqueReferenceId", "order for product already completed");
		if (!completedStatus.name().equals(orderProduct.getStatus().name()))
			throw new InvalidRequestException("completedStatus", "invalid completedStatus: %s", completedStatus.name());
		orderProduct.setStatus(Utils.getNextInSequence(this.statusSequence, completedStatus));
		return this.repository.save(orderProduct);
	}

	public List<OrderProduct> fetchAllByOrderId(final String orderId) {
		return this.repository.findAllByOrderId(orderId);
	}

}
