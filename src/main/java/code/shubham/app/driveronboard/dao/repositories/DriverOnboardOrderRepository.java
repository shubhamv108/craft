package code.shubham.app.driveronboard.dao.repositories;

import code.shubham.app.driveronboard.dao.entities.DriverOnboardOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverOnboardOrderRepository extends JpaRepository<DriverOnboardOrder, String> {

	Optional<DriverOnboardOrder> findByOrderReferenceId(String orderReferenceId);

}
