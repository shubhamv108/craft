package code.shubham.craft.driveronboard.dao.repositories;

import code.shubham.craft.driveronboard.dao.entities.DriverOnboardBackgroundVerification;
import code.shubham.craft.driveronboard.dao.entities.DriverOnboardOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverOnboardBackgroundVerificationRepository
		extends JpaRepository<DriverOnboardBackgroundVerification, String> {

	Optional<DriverOnboardBackgroundVerification> findByBackgroundVerificationReferenceId(
			String backgroundVerificationReferenceId);

}
