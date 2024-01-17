package code.shubham.app.driveronboard.dao.repositories;

import code.shubham.app.driveronboard.dao.entities.DriverOnboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverOnboardRepository extends JpaRepository<DriverOnboard, String> {

	Optional<DriverOnboard> findByDriverId(String driverId);

	List<DriverOnboard> findAllByDriverId(String driverId);

	Optional<DriverOnboard> findByIdAndUserId(String driverOnboardId, String userId);

	List<DriverOnboard> findAllByDriverIdAndUserId(String driverId, String userId);

}
