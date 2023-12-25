package code.shubham.craft.shipment.dao.repositories;

import code.shubham.craft.shipment.dao.entities.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShipmentTrackingRepository extends JpaRepository<Shipment, String> {

	Optional<Shipment> findByOrderId(String orderId);

	Optional<Shipment> findByUniqueReferenceId(String uniqueReferenceId);

}
