package code.shubham.app.driveronboard.dao.repositories;

import code.shubham.app.driveronboard.dao.entities.DriverOnboardBackgroundVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverOnboardBackgroundVerificationRepository
		extends JpaRepository<DriverOnboardBackgroundVerification, String> {

	Optional<DriverOnboardBackgroundVerification> findByBackgroundVerificationReferenceId(
			String backgroundVerificationReferenceId);

}
