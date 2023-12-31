package code.shubham.craft.driver.dao.repositories;

import code.shubham.craft.driver.dao.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, String> {

	Optional<Driver> findByUserId(String userId);

	Optional<Driver> findByIdAndUserId(String id, String userId);

}
