package code.shubham.craft.order.dao.repositories;

import code.shubham.craft.order.dao.entities.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, String> {

	List<OrderProduct> findAllByOrderId(String orderId);

	Optional<OrderProduct> findByUniqueReferenceId(String uniqueReferenceId);

}
