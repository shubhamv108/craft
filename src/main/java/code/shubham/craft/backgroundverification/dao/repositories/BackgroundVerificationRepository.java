package code.shubham.craft.backgroundverification.dao.repositories;

import code.shubham.craft.backgroundverification.dao.entities.BackgroundVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BackgroundVerificationRepository extends JpaRepository<BackgroundVerification, String> {

	Optional<BackgroundVerification> findByApplicantIdAndApplicantType(String driverId, String applicantType);

	Optional<BackgroundVerification> findByClientReferenceId(String clientReferenceId);

	List<BackgroundVerification> findAllByUserId(String userId);

}
