package code.shubham.app.driver.cab.dao.repositories;

import code.shubham.app.driver.cab.dao.entities.Cab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CabRepository extends JpaRepository<Cab, String> {

	Optional<Cab> findByRegistrationNumber(String registrationNumber);

	List<Cab> findAllByDriverIdAndUserId(String driverId, String userId);

	Optional<Cab> findAllByDriverIdAndUserIdAndRegistrationNumber(String driverId, String userId,
			String registrationNumber);

}
